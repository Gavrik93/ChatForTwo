package android.gava.chatfortwo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatMassageAdapter extends ArrayAdapter<ChatMassage> {

    private List <ChatMassage> message;
    private Activity activity;

    public ChatMassageAdapter(Activity context, int resource,
                                List<ChatMassage> messages) {
        super(context, resource, messages);

        this.message = messages;
        this.activity = context;
    }


    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater)activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ChatMassage chatMassage = getItem(position);
        int layoutRecource = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            layoutRecource = R.layout.my_massage_item;
        }else {
            layoutRecource = R.layout.your_massage_item;
        }


        if(convertView != null) {
            viewHolder = (ViewHolder)convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(
                    layoutRecource, parent, false
            );

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }




        viewHolder.messageTextView.setText(chatMassage.getText());
        viewHolder.nameTextView.setText(chatMassage.getName());

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        int flag;
        ChatMassage chatMassage = message.get(position);
        if (chatMassage.isMine()){
            flag = 0;
        } else {
            flag = 1;
        }

        return flag;

    }

    private class ViewHolder {
        private ImageView photoImageView;
        private TextView messageTextView;
        private TextView nameTextView;

        public ViewHolder(View view) {
            photoImageView = view.findViewById(R.id.photoImageView);
            messageTextView = view.findViewById(R.id.messageTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
        }
    }
}
