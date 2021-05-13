package wraith.atomic.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wraith.atomic.Atomic;
import wraith.atomic.Config;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AtomicRecipes {

    private static final HashSet<AtomicCombinerRecipe> ATOMIC_COMBINER_RECIPES = new HashSet<>();
    private static final HashMap<String, String> ATOMIC_COMPRESSOR_RECIPES = new HashMap<>();

    public static void loadAllRecipes() {
        loadAtomicCombinerRecipes();
        loadAtomicCompressorRecipes();
    }

    public static void loadAtomicCombinerRecipes() {
        JsonObject json = Config.getJsonObject(Config.readFile(new File("config/" + Atomic.MOD_ID + "/atomic_combiner.json")));
        JsonArray array = json.get("recipes").getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject recipeObject = element.getAsJsonObject();
            JsonObject output = recipeObject.getAsJsonObject("output");
            JsonObject inputsObject = recipeObject.getAsJsonObject("input");
            HashMap<String, Integer> inputs = new HashMap<>();
            for (Map.Entry<String, JsonElement> input : inputsObject.entrySet()) {
                inputs.put(input.getKey(), input.getValue().getAsInt());
            }
            AtomicCombinerRecipe recipe = new AtomicCombinerRecipe(output.get("item").getAsString(), output.get("amount").getAsInt(), inputs);
            ATOMIC_COMBINER_RECIPES.add(recipe);
        }
    }

    public static void loadAtomicCompressorRecipes() {
        JsonObject json = Config.getJsonObject(Config.readFile(new File("config/" + Atomic.MOD_ID + "/atomic_compressor.json"))).getAsJsonObject("recipes");
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            ATOMIC_COMPRESSOR_RECIPES.put(entry.getKey(), entry.getValue().getAsString());
        }
    }

    public static AtomicCombinerRecipe checkAtomicCombinerRecipe(Inventory inventory) {
        HashMap<String, Integer> items = new HashMap<>();
        for (int i = 0; i < inventory.size() - 1; ++i) {
            ItemStack item = inventory.getStack(i);
            if (item == ItemStack.EMPTY) {
                continue;
            }
            String id = Registry.ITEM.getId(item.getItem()).toString();
            if (!items.containsKey(id)) {
                items.put(id, item.getCount());
            } else {
                items.put(id, items.get(id) + inventory.getStack(i).getCount());
            }
        }
        for (AtomicCombinerRecipe recipeList : ATOMIC_COMBINER_RECIPES) {
            if (items.size() != recipeList.getInputItems().size()) {
                continue;
            }
            boolean foundRecipe = true;
            for (Map.Entry<String, Integer> input : recipeList.getInputItems().entrySet()) {
                boolean shouldSkip = true;
                foundRecipe = true;
                for (Map.Entry<String, Integer> inventoryItem : items.entrySet()) {
                    Item item = Registry.ITEM.get(new Identifier(inventoryItem.getKey()));
                    if ((input.getKey().startsWith("#") && TagRegistry.item(new Identifier(input.getKey().substring(1))).contains(item)) || input.getKey().equals(inventoryItem.getKey())) {
                        shouldSkip = false;
                    }
                }
                if (shouldSkip) {
                    foundRecipe = false;
                    break;
                }
            }
            if (foundRecipe) {
                return recipeList;
            }
        }
        return null;
    }

    public static String getAtomicCompressorRecipe(String input) {
        return ATOMIC_COMPRESSOR_RECIPES.getOrDefault(input, "");
    }
}
