package com.example.tony.myclock;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
 * Created by Jingguo Jiang on 28/11/2016.
 */

public class RingtoneAdapter extends BaseAdapter {

    private List <Map<String, Object>> ringData;
    private LayoutInflater layoutInflater;
    private Context context;
    private RadioButton ringRadioButton;

    public RingtoneAdapter(List ringData, Context context) {
        this.ringData = ringData;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return ringData.size();
    }

    @Override
    public Object getItem(int position) {
        return ringData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        RingTone ringTone = null;

        ringTone = new RingTone();
        view=layoutInflater.inflate(R.layout.ring_item_layout, null);
        ringRadioButton = ringTone.getRingRadioButton();
        ringRadioButton = (RadioButton) view.findViewById(R.id.ring_item);
        ringRadioButton.setText((String)ringData.get(position).get("title"));

        return view;
    }


    class RingTone {
        private RadioButton ringRadioButton;
        private String ringtoneName;


        public RadioButton getRingRadioButton() {
            return ringRadioButton;
        }

        public String getRingtoneName() {
            return ringtoneName;
        }

        public void setRingRadioButton(RadioButton ringRadioButton) {
            this.ringRadioButton = ringRadioButton;
        }

        public void setRingtoneName(String ringtoneName) {
            this.ringtoneName = ringtoneName;
        }
    }


}
