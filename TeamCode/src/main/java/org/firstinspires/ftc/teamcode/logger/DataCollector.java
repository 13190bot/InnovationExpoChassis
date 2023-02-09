package org.firstinspires.ftc.teamcode.logger;

import java.util.Vector;
import java.util.function.Supplier;

public class DataCollector {
    Vector data;
    Supplier dataSupplier;

    DataCollector(Supplier dataSupplier) {
        this.dataSupplier = dataSupplier;
        data = new Vector<>();
    }

    public void log () {
        data.addElement(dataSupplier.get());
    }

    public Vector getData() {
        return data;
    }
}