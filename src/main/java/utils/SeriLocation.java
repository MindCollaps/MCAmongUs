package utils;

import core.Plugin;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.UUID;

public class SeriLocation implements Serializable {

    private static final long serialVersionUID = 42L;

    public int x;
    public int y;
    public int z;
    public UUID world;

    public SeriLocation(Location location) {
        x = (int) location.getX();
        y = (int) location.getY();
        z = (int) location.getZ();
        world = location.getWorld().getUID();
    }

    public SeriLocation(int x, int y, int z, UUID world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Location turnIntoLocation(Plugin plugin) {
        try {
            return plugin.getServer().getWorld(world).getBlockAt(x, y, z).getLocation();
        } catch (Exception e){
            System.out.println("Error on converting SeriLocation in Location!");
            return null;
        }
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + " " + world;
    }
}
