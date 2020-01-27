package com.VMEDS.android;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.VMEDS.android.utils.Global_Typeface;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private Global_Typeface typeface;
    private int selectedPos;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, ExpandableListView mExpandableListView, int selectedPos) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        typeface = new Global_Typeface(_context);
        this.selectedPos = selectedPos;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);
        txtListChild.setTypeface(typeface.TypeFace_Roboto_Regular());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        try {
            childCount = this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        } catch (Exception e) {
            childCount = 0;
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        lblListHeader.setText(headerTitle);
        lblListHeader.setTypeface(typeface.TypeFace_Roboto_Regular());
//        if (groupPosition == selectedPos) {
//            lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.dblue));
//            lblListHeader.setTextColor(_context.getResources().getColor(R.color.white));
//        } else
        lblListHeader.setBackgroundColor(Color.TRANSPARENT);

        if (groupPosition == 0) {

            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu1, 0, 0, 0);
        } else if (groupPosition == 1) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu2, 0, 0, 0);

        } else if (groupPosition == 2) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu3, 0, 0, 0);

        } else if (groupPosition == 3) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu4, 0, 0, 0);
        } else if (groupPosition == 4) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu5, 0, 0, 0);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}