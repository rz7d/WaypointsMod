package pw.cinque.waypoints.storage;

import com.google.gson.Gson;
import pw.cinque.waypoints.entity.Waypoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

public class WaypointsStorage {

    private static volatile Reference<Gson> gson;

    private static Gson gson() {
        Gson g = gson == null ? null : gson.get();
        if (g == null) {
            g = new Gson();
            gson = new SoftReference<>(g);
        }
        return g;
    }

    private final Path storage;

    private final Set<Waypoint> waypoints = new HashSet<>();

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
