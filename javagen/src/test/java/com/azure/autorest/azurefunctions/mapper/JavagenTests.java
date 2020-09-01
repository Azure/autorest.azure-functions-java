package com.azure.autorest.azurefunctions.mapper;

import com.azure.autorest.azurefunctions.Javagen;
import com.azure.autorest.azurefunctions.extension.base.jsonrpc.Connection;
import org.junit.Test;

public class JavagenTests {
    @Test
    public void canParseCodeModel() {
        Javagen javagen = new MockJavagen(new Connection(System.out, System.in), "javagen", "session_1");
        javagen.processInternal();
    }
}
