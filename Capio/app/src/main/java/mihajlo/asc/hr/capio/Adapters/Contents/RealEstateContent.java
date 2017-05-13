package mihajlo.asc.hr.capio.Adapters.Contents;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mihajlo.asc.hr.capio.Models.Location;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableLocation;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableUnit;
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

    public static  void addItems(List<Unit> list, int price, List<Long> idsLike) {
        for (Unit unit : list) {
            if(unit.getPrice() < price) {
                Location currentLocation = unit.getLocation();
                String location = currentLocation.getStreetName() + " " +
                        currentLocation.getHouseNumber() + ", " + currentLocation.getCity();
                String backgroundUrl = unit.getImages().get(0).getUrl();

                boolean like = false;
                if (idsLike != null && idsLike.size() != 0 && idsLike.contains(unit.getId())) {
                    like = true;
                }
                RealEstateItem newItem = new RealEstateItem(unit.getId(),
                        String.valueOf((int) unit.getPrice()), location, backgroundUrl, like , new ParcelableUnit(unit));
                addItem(newItem);
            }
        }
    }

    private static void addItem(RealEstateItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void clearAll() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static class RealEstateItem implements Parcelable{
        private final Long id;
        public final String price;
        public final String location;
        public final String backgroundUrl;
        public boolean like;
        public ParcelableUnit unit;

        // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
        public static final Parcelable.Creator<RealEstateItem> CREATOR = new Parcelable.Creator<RealEstateItem>() {
            public RealEstateItem createFromParcel(Parcel in) {
                return new RealEstateItem(in);
            }

            public RealEstateItem[] newArray(int size) {
                return new RealEstateItem[size];
            }
        };

        // example constructor that takes a Parcel and gives you an object populated with it's values
        private RealEstateItem(Parcel in) {
            id = in.readLong();
            price = in.readString();
            location = in.readString();
            backgroundUrl = in.readString();
            like = in.readByte() != 0;
            unit = in.readParcelable(ParcelableUnit.class.getClassLoader());
        }

        public RealEstateItem(Long id, String price, String location,
                              String backgroundUrl, boolean like, ParcelableUnit unit) {
            this.id = id;
            this.price = price;
            this.location = location;
            this.backgroundUrl = backgroundUrl;
            this.like = like;
            this.unit = unit;
        }

        /* everything below here is for implementing Parcelable */

        // 99.9% of the time you can just ignore this
        @Override
        public int describeContents() {
            return 0;
        }

        // write your object's data to the passed-in Parcel
        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(id);
            out.writeString(price);
            out.writeString(location);
            out.writeString(backgroundUrl);
            out.writeByte((byte) (like ? 1 : 0));
            out.writeParcelable(unit, flags);
        }

        public ParcelableUnit getUnit() {
            return unit;
        }

        public boolean isLike() {
            return like;
        }

        public void setLike(boolean like) {
            this.like = like;
        }

    }
}
