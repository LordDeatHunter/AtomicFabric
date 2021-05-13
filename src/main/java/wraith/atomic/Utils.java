package wraith.atomic;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {

    public static Identifier ID(String path) {
        return new Identifier(Atomic.MOD_ID, path);
    }

    public static void saveInventoryToStack(ItemStack stack, Inventory inventory, int size) {
        if (inventory.size() < size) {
            return;
        }
        CompoundTag tag = stack.getOrCreateSubTag("Atomic");
        DefaultedList<ItemStack> items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; ++i) {
            items.set(i, inventory.getStack(i));
        }
        Inventories.toTag(tag, items);
    }


    public static void saveFilesFromJar(String dir, String outputDir, boolean overwrite) {
        JarFile jar = null;
        try {
            jar = new JarFile(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            ModMetadata metadata = FabricLoader.getInstance().getModContainer(Atomic.MOD_ID).get().getMetadata();
            String filename = "mods/wraith-" + Atomic.MOD_ID + "-" + metadata.getVersion() + ".jar";
            try {
                jar = new JarFile(new File(filename));
            } catch (IOException ignored) {
            }
        }

        if (jar != null) {
            Enumeration<JarEntry> entries = jar.entries();
            while(entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if ((entry.getName().endsWith(".png") || entry.getName().endsWith(".mcmeta")) && !dir.contains("textures")) {
                    continue;
                }
                if (!entry.getName().startsWith(dir) || (!entry.getName().endsWith(".json") && !entry.getName().endsWith(".png") && !entry.getName().endsWith(".mcmeta"))) {
                    continue;
                }
                String[] segments = entry.getName().split("/");
                String filename = segments[segments.length - 1];
                if (entry.isDirectory()) {
                    continue;
                }
                InputStream is = Utils.class.getResourceAsStream("/" + entry.getName());
                String path = "config/" + Atomic.MOD_ID + "/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                if (filename.endsWith(".png")) {
                    if (Files.exists(new File(path).toPath()) && overwrite) {
                        try {
                            Files.delete(new File(path).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!Files.exists(new File(path).toPath())) {
                        try {
                            new File(path).getParentFile().mkdirs();
                            Files.copy(is, new File(path).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        inputStreamToFile(is, new File(path), overwrite);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Launched from IDE.");
            File[] files = FabricLoader.getInstance().getModContainer(Atomic.MOD_ID).get().getPath(dir).toFile().listFiles();
            for(File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                String[] segments = file.getName().split("/");
                String filename = segments[segments.length - 1];
                String path = "config/" + Atomic.MOD_ID + "/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                if (filename.endsWith(".png")) {
                    if (Files.exists(new File(path).toPath()) && overwrite) {
                        try {
                            Files.delete(new File(path).toPath());
                        } catch (IOException e) {
                            Atomic.LOGGER.warn("ERROR OCCURRED WHILE DELETING OLD TEXTURES FOR " + filename);
                            e.printStackTrace();
                        }
                    }
                    if (!Files.exists(new File(path).toPath())) {
                        try {
                            Atomic.LOGGER.info("Regenerating " + filename);
                            new File(path).getParentFile().mkdirs();
                            Files.copy(file.toPath(), new File(path).toPath());
                        } catch (IOException e) {
                            Atomic.LOGGER.warn("ERROR OCCURRED WHILE REGENERATING " + filename + " TEXTURE");
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Config.createFile(path, FileUtils.readFileToString(file, StandardCharsets.UTF_8), overwrite);
                    } catch (IOException e) {
                        Atomic.LOGGER.warn("ERROR OCCURRED WHILE REGENERATING " + filename + " TEXTURE");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file, boolean overwrite) throws IOException {
        if (!file.exists() || overwrite) {
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
    }

    public static InputStream stringToInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    public static final Random random = new Random(Calendar.getInstance().getTimeInMillis());
    public static int getRandomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }


}
