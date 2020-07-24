package com.kartega.ophelia.ea_spinner.interfaces;

import java.util.HashMap;

public interface SpinnerMultipleListener<T> {
    void onItemsSelected(HashMap<String, T> selections);
}
