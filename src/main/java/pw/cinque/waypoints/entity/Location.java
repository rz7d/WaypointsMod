package pw.cinque.waypoints.entity;

import net.minecraft.util.Vec3;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Location class represents a location of the world.
 */
public final class Location {

    @Nonnull
    public static Location of(int x, int y, int z, @Nonnull String world) {
        Objects.requireNonNull(world);
        return new Location(x, y, z, world);
    }

    @Nonnull
    public static Location of(double x, double y, double z, @Nonnull String world) {
        return of((int) x, (int) y, (int) z, world);
    }

    @Nonnull
    public static Location of(Vec3 vec3, @Nonnull String world) {
        return of(vec3.xCoord, vec3.yCoord, vec3.zCoord, world);
    }

    private final int x;
    private final int y;
    private final int z;

    private final String world;

    public Location(int x, int y, int z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public String world() {
        return world;
    }

    public Vec3 toVec3() {
        return Vec3.createVectorHelper(x(), y(), z());
    }

}
