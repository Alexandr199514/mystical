package com.skycat.mystical.network;

import com.skycat.mystical.MysticalClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class ClientNetworkHandler implements ClientPlayNetworking.PlayPayloadHandler {

    @Override
    public void receive(CustomPayload payload, ClientPlayNetworking.Context context) {
        if (payload instanceof ActiveSpellsPacket spellsPacket) {
            MysticalClient.HUD_MANAGER.updateCachedSpells(spellsPacket.spells());
        }
    }
}
