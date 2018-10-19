package kz.ncanode.log;

import kz.ncanode.Helper;
import kz.ncanode.config.ConfigServiceProvider;
import kz.ncanode.ioc.ServiceProvider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ErrorLogServiceProvider extends Log implements ServiceProvider {

    OutLogServiceProvider out = null;
    ConfigServiceProvider config = null;

    private String errorLogFile = "";

    public ErrorLogServiceProvider(ConfigServiceProvider config, OutLogServiceProvider out) {

        this.out = out;
        this.config = config;

        try {
            errorLogFile = this.config.get("log", "error_log");
            errorLogFile = Helper.absolutePath(errorLogFile);

            os = new FileOutputStream(errorLogFile);
            ps = new LogPrintStream(os);
        } catch (FileNotFoundException e) {
            this.out.write("WARNING! Cannot create error log file at: " + errorLogFile);
        }
    }

    @Override
    public void write(String msg) {
        super.write(msg);
        out.write(msg); // дублируем вывод ошибок в стандартный вывод
    }
}