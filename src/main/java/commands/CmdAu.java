package commands;

import amongUs.AUGameHandler;
import core.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

public class CmdAu implements MCCommand {

    private Plugin plugin;
    private AUGameHandler gameHandler;

    public CmdAu(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean calledPlayer(String[] args, CommandSender sender, Command command, Plugin plugin) {
        return true;
    }

    @Override
    public void actionPlayer(String[] args, CommandSender sender, Command command, Plugin plugin) {
        if (args.length > 0) {
            switch (args[0]) {
                case "create":
                    gameHandler = new AUGameHandler(plugin);
                    gameHandler.addPlayerToGame((Player) sender);
                    sender.sendMessage("§2Das Spiel wurde erstellt und du wurdest hinzugefügt!");
                    break;

                case "start": {
                    if (gameHandler == null) {
                        sender.sendMessage("§4Es wurde noch kein Spiel erstellt!");
                        return;
                    }

                    gameHandler.startGame();
                }
                break;

                case "add":
                    if (gameHandler == null) {
                        sender.sendMessage("§4Es wurde noch kein Spiel erstellt! Es wird nun eines erstellt!");
                        gameHandler = new AUGameHandler(plugin);
                        sender.sendMessage("§2Das Spiel wurde erstellt und du wurdest hinzugefügt!");
                    }
                    if (args.length > 1) {
                        Player player = Bukkit.getServer().getPlayer(args[1]);
                        if (player == null) {
                            sender.sendMessage("§4Der Spieler wurde nicht gefunden");
                            return;
                        }

                        if (gameHandler.containtsPlayer(player)) {
                            sender.sendMessage("§4Der Spieler ist bereits im Spiel!");
                            return;
                        }

                        gameHandler.addPlayerToGame(player);
                        sender.sendMessage("§2Spieler hinzugefügt!");
                    } else {
                        sender.sendMessage("§4Der Spieler wurde nicht gefunden");
                    }
                    break;

                case "test":
                    Player s = (Player) sender;
                    break;

                case "stop":
                    if (gameHandler == null) {
                        sender.sendMessage("§4Es wurde noch kein Spiel erstellt!");
                        return;
                    }
                    gameHandler.abortGame();
                    gameHandler = null;
                    break;
            }
        }
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command, Plugin plugin) {

    }

    @Override
    public boolean isShownInConsole() {
        return false;
    }

    @Override
    public String help() {
        return "start - starts the game\n" +
                "stop - stops the game";
    }

    public AUGameHandler getGameHandler() {
        return gameHandler;
    }
}
