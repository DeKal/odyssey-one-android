//package adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.tan_pc.navigationdraweractivity.R;
//import com.jakewharton.picasso.OkHttp3Downloader;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//import java.util.List;
//
//import com.odyssey.one.app.common.api.AccountVerification;
//import retrofit2.Callback;
//
//
//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
//
//    private List<AccountVerification> dataList;
//    private Context context;
//
//    public CustomAdapter(Callback<List<AccountVerification>> context, List<AccountVerification> dataList){
//        this.context = (Context) context;
//        this.dataList = dataList;
//    }
//
//    class CustomViewHolder extends RecyclerView.ViewHolder {
//
//        public final View mView;
//
//        TextView txtName;
//        TextView txtPass;
//        TextView txtRole;
//
//        private TextView txtUser;
//
//        CustomViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
////
//            txtName = (TextView) mView.findViewById(R.id.userName);
//            txtPass = (TextView) mView.findViewById(R.id.userPass);
//            txtRole = (TextView) mView.findViewById(R.id.role);
//
//        }
//    }
//
//    @Override
//    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.user_row, parent, false);
//        return new CustomViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(CustomViewHolder holder, int position) {
//        holder.txtName.setText(dataList.get(position).getUsername());
//
//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getUsername())
//                .placeholder((R.id.userName))
//                .error(R.id.userName)
//                .into((Target) holder.txtUser);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//}
