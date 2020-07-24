package com.kartega.ophelia.ea_spinner.interfaces;

import java.util.HashMap;

public interface SpinnerMultipleSelectionListener<T> {
    void onItemsSelected(HashMap<String, T> items);
}
