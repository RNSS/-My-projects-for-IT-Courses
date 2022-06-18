package com.example.Diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Multi_infoAdapter extends RecyclerView.Adapter<Multi_infoAdapter.MyViewHolder> {

    private final Context mContext;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<Multi_info> userList = new ArrayList<>();

    public Multi_infoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Multi_info> Multi_info) {
        userList.clear();
        userList.addAll(Multi_info);
        notifyDataSetChanged();
    }

    @Override
    public Multi_infoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Multi_infoAdapter.MyViewHolder holder, final int position) {

        final Multi_info user = userList.get(position);

        holder.myTitleTextView.setText(user.getTitle());
        holder.myDescriptionTextView.setText(user.getAdditional());
        holder.dateTextView.setText(user.getDiary());


        holder.myButtonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(view.getContext(), Update.class);
             i.putExtra("UserId",userList.get(position).getUserID());
             i.putExtra("idNum",userList.get(position).getID_Number());
                i.putExtra("title",userList.get(position).getTitle());
                i.putExtra("diary",userList.get(position).getDiary());
                i.putExtra("additional",userList.get(position).getAdditional());

                view.getContext().startActivity(i);

             }
        });

        // delete user from firebase database based upon the key
        holder.myButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder viewDetail = new AlertDialog.Builder(mContext);



                        viewDetail.setIcon(R.drawable.danger);
                        viewDetail.setTitle("confirm ");
                        viewDetail.setMessage(
                                "Are you sure to delete this diary ? " );
                        viewDetail.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        final String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                       String key= userList.get(position).getID_Number();
                                        databaseReference.child("Users").child( userId).child("diary").child(key).removeValue();
                                        dialog.dismiss();
                                        Intent intent1=new Intent(mContext, Home.class);

                                        mContext.startActivity(intent1);
                                        ((Activity)mContext).finish();
                                    }
                                });
                        viewDetail.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                });
                        viewDetail.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTeensCount;
        public TextView myTitleTextView;
        public TextView myDescriptionTextView;
        public TextView dateTextView;

        public TextView mCountChild;
        public CircleImageView myButtonDelete,myButtonedit;
        public MyViewHolder(View itemView) {
            super(itemView);

            myTitleTextView = (TextView) itemView.findViewById(R.id.title);
            myDescriptionTextView = (TextView) itemView.findViewById(R.id.description);
            dateTextView= (TextView) itemView.findViewById(R.id.txtdate);
            myButtonDelete =  itemView.findViewById(R.id.deleteButton);
            myButtonedit = itemView.findViewById(R.id.editButton);
        }
    }
    public void shownDialog(final int position){

    }
}
