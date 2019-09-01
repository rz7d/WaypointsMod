package pw.cinque.waypoints.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import java.awt.*;
import java.io.*;

public final class Waypoint {

    // public static Waypoint of(Location location, String name, Color color, String address) {
    //     // lastIndexOf: for IPv6 support
    //     int sep = address.lastIndexOf(':');
    //
    //     InetSocketAddress server;
    //     if (sep + 1 > address.length()) {
    //         server = new InetSocketAddress(address, 25565);
    //     } else {
    //         server = new InetSocketAddress(
    //             sep == -1 ? address : address.substring(0, sep),
    //             sep == -1 ? 25565 : Integer.parseInt(address.substring(sep + 1)));
    //     }
    //
    //     return new Waypoint(location, name, color, server);
    // }

    public static Waypoint of(Location location, String name, Color color, String server) {
        return new Waypoint(location, name, color, server);
    }

    private final Location location;

    private final String name;
    private final Color color;

    private final String server;

    private Waypoint(Location location, String name, Color color, String server) {
        this.location = location;
        this.name = name;
        this.color = color;
        this.server = server;
    }

    public Location location() {
        return location;
    }

    public String name() {
        return name;
    }

    public Color color() {
        return color;
    }

    public String server() {
        return server;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRender() {
        final Minecraft mc = Minecraft.getMinecraft();
        return !mc.isSingleplayer()
            && mc.theWorld.provider.getDimensionName().equals(location.world())
            && mc.getCurrentServerData().serverIP.equals(server);
    }

    @SideOnly(Side.CLIENT)
    public double distanceTo(Entity en) {
        final Location location = this.location;
        final double x = location.x() - en.posX;
        final double y = location.y() - en.posY;
        final double z = location.z() - en.posZ;
        return Math.sqrt(x * x + y * y + z * z);
    }

    public byte[] save() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeInt(location.x());
            out.writeInt(location.y());
            out.writeInt(location.z());
            out.writeUTF(location.world());
            out.writeUTF(name);
            out.writeInt(color.getRGB());
            out.writeObject(server);
        }
        return buffer.toByteArray();
    }

    public static Waypoint load(byte[] data) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            int x = in.readInt();
            int y = in.readInt();
            int z = in.readInt();
            String world = in.readUTF();
            String name = in.readUTF();
            Color color = new Color(in.readInt());
            String server = in.readUTF();
            return of(Location.of(x, y, z, world), name, color, server);
        }
    }

}
