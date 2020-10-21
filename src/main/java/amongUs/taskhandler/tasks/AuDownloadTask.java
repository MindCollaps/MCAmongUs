package amongUs.taskhandler.tasks;

import amongUs.AUGameHandler;
import amongUs.AUPlayer;
import amongUs.taskhandler.AuTaskAnimationHandler;
import amongUs.taskhandler.AuTaskType;
import core.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import utils.SeriLocation;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AuDownloadTask implements AUTask {

    private SeriLocation location;
    public transient AuTaskType taskType = AuTaskType.LongTask;
    public transient HashMap<UUID, AuTaskAnimationHandler> anis = new HashMap<>();
    public transient AuUploadTask nextTask = new AuUploadTask();

    @Override
    public SeriLocation getLocation() {
        return location;
    }

    @Override
    public AuTaskType getTaskType() {
        return taskType;
    }

    @Override
    public AUTask getNextTask() {
        return nextTask;
    }

    @Override
    public void playerPerformTask(final AUPlayer player, final AUGameHandler gameHandler, final Plugin plugin) {
        final Inventory inv = Bukkit.createInventory(null, 9 * 3, "Download");
        plugin.getInventoryListener().unremovableItemsInventory.put(player.player.getUniqueId(), inv);
        player.player.openInventory(inv);
        //1
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                inv.setItem(invLocation(2, 2), new ItemStack(Material.GREEN_WOOL));
                //2
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        //3
                        inv.setItem(invLocation(3, 2), new ItemStack(Material.GREEN_WOOL));
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                //4
                                inv.setItem(invLocation(4, 2), new ItemStack(Material.GREEN_WOOL));
                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        //5
                                        inv.setItem(invLocation(5, 2), new ItemStack(Material.GREEN_WOOL));
                                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                //6
                                                inv.setItem(invLocation(6, 2), new ItemStack(Material.GREEN_WOOL));
                                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //7
                                                        inv.setItem(invLocation(7, 2), new ItemStack(Material.GREEN_WOOL));
                                                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                player.tasks.add(nextTask);
                                                                nextTask.activateNextTask(player, gameHandler, plugin);
                                                                plugin.getInventoryListener().unremovableItemsInventory.remove(player.player.getUniqueId());
                                                                inv.clear();
                                                                player.player.closeInventory();
                                                                anis.get(player.player.getUniqueId()).stopAnimation();
                                                            }
                                                        }, ThreadLocalRandom.current().nextInt(10, 40));
                                                    }
                                                }, ThreadLocalRandom.current().nextInt(10, 40));
                                            }
                                        }, ThreadLocalRandom.current().nextInt(10, 40));
                                    }
                                }, ThreadLocalRandom.current().nextInt(10, 40));
                            }
                        }, ThreadLocalRandom.current().nextInt(10, 40));
                    }
                }, ThreadLocalRandom.current().nextInt(10, 40));
            }
        }, ThreadLocalRandom.current().nextInt(10, 40));
    }

    @Override
    public void setupTask(Player player, Plugin plugin) {
        location = new SeriLocation(player.getLocation());
    }

    @Override
    public void gameStart(Plugin plugin, AUPlayer player, AUGameHandler gameHandler) {
        anis.put(player.player.getUniqueId(), new AuTaskAnimationHandler(getLocation().turnIntoLocation(plugin), player.player));
        anis.get(player.player.getUniqueId()).startAnimation(plugin);
    }

    @Override
    public void abort(AUPlayer player, AUGameHandler gameHandler, Plugin plugin) {
        anis.get(player.player.getUniqueId()).stopAnimation();
    }

    @Override
    public void activateNextTask(AUPlayer player, AUGameHandler gameHandler, Plugin plugin) {

    }

    private int invLocation(int spalte, int zeile) {
        return (9 * (zeile - 1)) + (spalte - 1);
    }
}
