package com.example.drummond.voteapp;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Drummond on 2015/10/21.
 */
public class AddItemAdpater extends BaseAdapter{

    private LayoutInflater m_Inflater;
    private ArrayList<String> text;

    public AddItemAdpater(Context context){
        text = new ArrayList<String>();
        text.add("第一项");// 默认只加载一个Item
        this.m_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return text.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return text.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = m_Inflater.inflate(R.layout.edit_item, null);
            holder.btnOpen = (ImageButton) convertView
                    .findViewById(R.id.btn_add_item);
            holder.editText = (EditText) convertView
                    .findViewById(R.id.et_item_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (position) {// 不要以为XML文件中是2个按钮，其实只有一个
            case 0:
                holder.btnOpen.setBackgroundResource(R.drawable.additem);// 第一项按钮则显示加号图片
                holder.editText.setHint("选项1");
                break;

            default:
                holder.btnOpen.setBackgroundResource(R.drawable.removeitem);// 超过了一项则显示减号图片，可以删除
                StringBuffer hint = new StringBuffer("选项");
                String num = String.valueOf(position + 1);
                holder.editText.setHint(hint.append(num));
                break;
        }
        holder.btnOpen.setOnClickListener(new View.OnClickListener() {// 添加按钮

            public void onClick(View v) {
                if (position == 0) {
                    text.add("xxx");// 添加一项控件

                } else if (position > 0) {// 始终留一项不能删除

                    text.remove(text.size() - 1);// 删除按钮
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public final class ViewHolder {
        public EditText editText;
        public ImageButton btnOpen;

    }
}
