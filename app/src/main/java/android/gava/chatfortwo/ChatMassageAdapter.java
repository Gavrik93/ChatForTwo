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


        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater()
                    .inflate(R.layout.massage_item, parent, false);
        }

        ImageView photoImageView = convertView
                .findViewById(R.id.photoImageView);
        TextView textTextView = convertView
                .findViewById(R.id.textTextView);
        TextView nameTextView = convertView
                .findViewById(R.id.nameTextView);

        ChatMassage message = getItem(position);

        boolean isText = message.getImageUrl() == null;
        if(isText){
            textTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            textTextView.setText(message.getText());
        } else {
            textTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                        .load(message.getImageUrl())
                        .into(photoImageView);
        }

        nameTextView.setText(message.getName());

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
