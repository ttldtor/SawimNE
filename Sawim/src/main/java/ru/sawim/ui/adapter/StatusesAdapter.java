package ru.sawim.ui.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import protocol.Protocol;
import protocol.StatusInfo;
import ru.sawim.R;
import ru.sawim.Scheme;
import ru.sawim.icons.Icon;
import ru.sawim.ui.fragment.StatusesFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Gerc
 * Date: 26.01.13
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class StatusesAdapter extends BaseAdapter {

    StatusInfo statusInfo;

    private int type;
    private int selectedItem;

    public StatusesAdapter(Protocol p, int type) {
        statusInfo = p.getStatusInfo();
        this.type = type;
    }

    @Override
    public int getCount() {
        return statusInfo.applicableStatuses.length;
    }

    @Override
    public Integer getItem(int i) {
        return (int) statusInfo.applicableStatuses[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
    }

    @Override
    public View getView(int position, View convView, ViewGroup viewGroup) {
        ItemWrapper wr;
        View row = convView;
        if (row == null) {
            LayoutInflater inf = LayoutInflater.from(viewGroup.getContext());
            row = inf.inflate(R.layout.status_item, null);
            wr = new ItemWrapper(row);
            row.setTag(wr);
        } else {
            wr = (ItemWrapper) row.getTag();
        }
        int item = getItem(position);
        LinearLayout activeItem = (LinearLayout) row;
        if (item == selectedItem) {
            activeItem.setBackgroundColor(Scheme.getColor(R.attr.item_selected));
        } else {
            activeItem.setBackgroundColor(0);
        }
        wr.populateFrom(item);
        return row;
    }

    public class ItemWrapper {
        View item = null;
        private TextView itemStatus = null;
        private ImageView itemImage = null;

        public ItemWrapper(View item) {
            this.item = item;
            itemImage = (ImageView) item.findViewById(R.id.status_image);
            itemStatus = (TextView) item.findViewById(R.id.status);
        }

        void populateFrom(int item) {
            if (type == StatusesFragment.ADAPTER_STATUS) {
                Icon ic = statusInfo.getIcon((byte) item);
                itemStatus.setTextColor(Scheme.getColor(R.attr.text));
                itemStatus.setText(statusInfo.getName((byte) item));
                if (ic != null) {
                    itemImage.setVisibility(ImageView.VISIBLE);
                    itemImage.setImageDrawable(ic.getImage());
                } else {
                    itemImage.setVisibility(ImageView.GONE);
                }
            }
            if (item == selectedItem) {
                itemStatus.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                itemStatus.setTypeface(Typeface.DEFAULT);
            }
        }
    }
}