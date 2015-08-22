/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 * 
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.apiculture;

import forestry.api.core.INBTTagable;

/**
 * Stores beekeeping logic for bee housings.
 * Get one with BeeManager.beeRoot.createBeekeepingLogic(IBeeHousing housing)
 * Save and load it to NBT using the INBTTagable methods.
 */
public interface IBeekeepingLogic extends INBTTagable {

	/* SERVER */

	/**
	 * Checks that the bees can work, setting error conditions on the housing where needed
	 * @return true if no errors are present and doWork should be called
	 */
	boolean canWork();

	/**
	 * Performs actual work, breeding, production, etc.
	 */
	void doWork();


	/* CLIENT */

	/**
	 * Call this when the housing comes into view of the Client.
	 * (i.e. when tile.getDescriptionPacket() is called)
	 */
	void syncToClient();

	/**
	 * Get the progress bar for breeding and production.
	 * To avoid network spam, this is only available Server-side,
	 * and must be synced manually to the Client when a GUI is open.
	 */
	int getBeeProgressPercent();

	/**
	 * Whether bee fx should be active.
	 * Internally, this is automatically synced to the Client.
	 */
	boolean canDoBeeFX();

	/**
	 * Display bee fx. Calls IBee.doFX(IEffectData[] storedData, IBeeHousing housing) on the queen.
	 * Internally, the queen is automatically synced to the Client for the fx.
	 */
	void doBeeFX();

}
