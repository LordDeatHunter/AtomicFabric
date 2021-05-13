package wraith.atomic.recipe;

import java.util.HashMap;

public class AtomicCombinerRecipe {

    private final String outputItem;
    private final int outputAmount;
    private final HashMap<String, Integer> inputItems;

    public AtomicCombinerRecipe(String outputItem, int outputAmount, HashMap<String, Integer> inputItems) {
        this.outputItem = outputItem;
        this.outputAmount = outputAmount;
        this.inputItems = inputItems;
    }

    public String getOutputItem() {
        return outputItem;
    }

    public int getOutputAmount() {
        return outputAmount;
    }

    public HashMap<String, Integer> getInputItems() {
        return inputItems;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AtomicCombinerRecipe r = (AtomicCombinerRecipe) obj;
        return r.outputItem.equals(this.outputItem) && r.outputAmount == this.outputAmount && r.inputItems.equals(this.inputItems);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + outputItem.hashCode();
        hash = 31 * hash + Integer.hashCode(outputAmount);
        hash = 31 * hash + inputItems.hashCode();
        return hash;
    }

}
