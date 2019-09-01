package pw.cinque.waypoints.storage;

import com.google.gson.Gson;
import pw.cinque.waypoints.entity.Waypoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

public class WaypointsStorage {

    private static volatile Reference<Gson> gson = new SoftReference<>(new Gson());

    private static Gson gson() {
        Gson g = gson.get();
        if (g == null) {
            gson = new WeakReference<>(g = new Gson());
        }
        return g;
    }

    private final Path storage;

    private Set<Waypoint> waypoints = new HashSet<>();

    public WaypointsStorage(Path storage) {
        this.storage = storage;
    }

    public void load() throws IOException {
        if (Files.exists(storage)) {
            Waypoint[] waypoints;
            try (BufferedReader reader = Files.newBufferedReader(storage, UTF_8)) {
                waypoints = gson().fromJson(reader, Waypoint[].class);
                if (waypoints == null) {
                    return;
                }
            }
            this.waypoints.addAll(Arrays.asList(waypoints));
        }
    }

    public void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(storage, UTF_8, WRITE, CREATE, TRUNCATE_EXISTING))) {
            writer.write(gson().toJson(this.waypoints));
        }
    }

    public Set<Waypoint> waypoints() {
        return waypoints;
    }

}
