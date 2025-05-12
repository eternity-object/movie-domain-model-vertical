package org.eternity.domainmodel.movie.service;

import lombok.SneakyThrows;

public class ExternalService {
    @SneakyThrows
    public static void execute() {
        Thread.sleep(10000);
    }
}
