package android.gava.chatfortwo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private List<ChatMessage> message;
    private Activity activity;

    public ChatMessageAdapter(Activity context, int resource,
                              List<ChatMessage> messages) {
        super(context, resource, messages);

        this.message = messages;
        this.activity = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ChatMessage chatMessage = getItem(position);
        int layoutRecource = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            layoutRecource = R.layout.my_message_item;
        } else {
            layoutRecource = R.layout.your_message_item;
        }


        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(
                    layoutRecource, parent, false
            );

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }


        viewHolder.messageTextView.setText(chatMessage.getText());
        viewHolder.nameTextView.setText(chatMessage.getName());


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        int flag;
        ChatMessage chatMassage = message.get(position);
        if (chatMassage.isMine()) {
            flag = 0;
        } else {
            flag = 1;
        }

        return flag;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
