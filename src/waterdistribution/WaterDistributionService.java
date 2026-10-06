package waterdistribution;

import java.util.*;

public class WaterDistributionService {
    private final WaterNetwork network;

    public WaterDistributionService(WaterNetwork network) {
        this.network = network;
    }

    public void distributeWater() {
        int totalDemand = network.getHouses().values().stream().mapToInt(House::getWaterDemand).sum();
        int totalSupply = network.getResources().values().stream().mapToInt(WaterResource::getAvailableWater).sum();

        System.out.println("Total demand: " + totalDemand);
        System.out.println("Total available supply: " + totalSupply);

        for (House house : network.getHouses().values()) {
            int remaining = house.getWaterDemand();
            List<String> route = findResourceRoute(house.getId());

            if (route.isEmpty()) {
                System.out.println(house.getId() + " -> unreachable");
                continue;
            }

            for (WaterResource resource : network.getResources().values()) {
                if (remaining == 0) break;
                if (network.findRouteDFS(resource.getId(), house.getId()).isEmpty()) continue;
                remaining -= resource.supply(remaining);
            }

            if (remaining == 0)
                System.out.println(house.getId() + " -> supplied " + house.getWaterDemand() + " units via " + route);
            else
                System.out.println(house.getId() + " -> shortage " + remaining + " units via " + route);
        }
    }

    private List<String> findResourceRoute(String houseId) {
        for (String resourceId : network.getResources().keySet()) {
            List<String> route = network.findRouteDFS(resourceId, houseId);
            if (!route.isEmpty()) return route;
        }
        return List.of();
    }
}
