package com.example.coffeebakery.DetailReceipt.interfaces;


import com.example.coffeebakery.DetailReceipt.Driver;

public interface FirebaseDriverListener {

    void onDriverAdded(Driver driver);

    void onDriverRemoved(Driver driver);

    void onDriverUpdated(Driver driver);

}
