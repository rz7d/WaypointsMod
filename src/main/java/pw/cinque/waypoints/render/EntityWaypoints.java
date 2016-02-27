package pw.cinque.waypoints.render;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import pw.cinque.waypoints.Waypoint;
import pw.cinque.waypoints.WaypointsMod;

public class EntityWaypoints extends Entity {
	
	public EntityWaypoints(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}

	@Override
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public float getBrightness(float f) {
		return 1F;
	}

}
