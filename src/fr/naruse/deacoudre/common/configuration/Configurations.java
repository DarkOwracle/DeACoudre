package fr.naruse.deacoudre.common.configuration;

import fr.naruse.deacoudre.main.DacPlugin;

public class Configurations {
    private MessagesConfiguration messages;
    private StatisticsConfiguration statistics;
    private ManagerConfiguration managerConfiguration;
    private CommandsConfiguration commands;
    public Configurations(DacPlugin pl){
        this.messages = new MessagesConfiguration(pl);
        this.statistics = new StatisticsConfiguration(pl);
        this.managerConfiguration = new ManagerConfiguration(pl.getDacPlugin());
        this.commands = new CommandsConfiguration(pl);
        pl.getConfig().set("logs", pl.getConfig().getBoolean("logs"));
        pl.getConfig().set("block", pl.getConfig().getStringList("block"));
        pl.saveConfig();
    }

    public CommandsConfiguration getCommands() {
        return commands;
    }

    public StatisticsConfiguration getStatistics() {
        return statistics;
    }

    public MessagesConfiguration getMessages() {
        return messages;
    }

    public ManagerConfiguration getManager() {
        return managerConfiguration;
    }
}
