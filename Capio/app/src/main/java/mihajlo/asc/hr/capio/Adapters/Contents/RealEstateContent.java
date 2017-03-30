package mihajlo.asc.hr.capio.Adapters.Contents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mihajlo.asc.hr.capio.Models.Location;
import mihajlo.asc.hr.capio.Models.Unit;

/**
 * Created by Damjan on 3/30/2017.
 */

public class RealEstateContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<RealEstateItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Long, RealEstateItem> ITEM_MAP = new HashMap<>();

    public static  void addItems(List<Unit> list) {
        for (Unit unit : list) {
            Location currentLocation = unit.getLocation();
            String location = currentLocation.getStreetName() + " " +
                    currentLocation.getHouseNumber() + ", " + currentLocation.getCity();
            String backgroundUrl = unit.getImages().get(0).getUrl();
            RealEstateItem newItem = new RealEstateItem(unit.getId(),
                    String.valueOf(unit.getPrice()), location, backgroundUrl, unit);
            addItem(newItem);
        }
    }

    private static void addItem(RealEstateItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class RealEstateItem {
        private final Long id;
        public final String price;
        public final String location;
        public final String backgroundUrl;
        public Unit unit;

        public RealEstateItem(Long id, String price, String location,
                              String backgroundUrl, Unit unit) {
            this.id = id;
            this.price = price;
            this.location = location;
            this.backgroundUrl = backgroundUrl;
            this.unit = unit;
        }
    }
}
