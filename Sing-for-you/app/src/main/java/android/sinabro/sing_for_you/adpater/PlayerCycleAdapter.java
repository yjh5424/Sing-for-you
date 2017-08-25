package android.sinabro.sing_for_you.adpater;

import android.content.Context;
import android.sinabro.sing_for_you.R;
import android.sinabro.sing_for_you.model.PlayerItem;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class PlayerCycleAdapter extends PagerAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<PlayerItem> playerItems;

    public PlayerCycleAdapter(ArrayList<PlayerItem> players, Context mContext) {
        this.playerItems=players;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return playerItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.player_item,container,false);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView singerTextView = (TextView) view.findViewById(R.id.titleTextView);
        imageView.setImageResource(playerItems.get(position).getImage());
        titleTextView.setText(playerItems.get(position).getTitle());
        singerTextView.setText(playerItems.get(position).getSinger());
        container.addView(view);
        return view;
    }
}
