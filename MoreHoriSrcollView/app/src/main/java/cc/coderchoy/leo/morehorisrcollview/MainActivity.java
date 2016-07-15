package cc.coderchoy.leo.morehorisrcollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    SlideLeftToLoadMoreView move_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_scroll_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);

        initData();
        adapter=new MyRecyclerViewAdapter(this,mDatas);
        recyclerView.setAdapter(adapter);
        move_view=(SlideLeftToLoadMoreView)findViewById(R.id.move_view);

        move_view.setOnShowMoreListener(new SlideLeftToLoadMoreView.OnShowMoreListener() {
            @Override
            public void onShowMore() {
                Toast.makeText(MainActivity.this,"更多 ",Toast.LENGTH_SHORT).show();
            }
        });
//        recyclerView.setAdapter(new RecyclerView.Adapter() {
//
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                Button button = new Button(parent.getContext());
//                button.setText("gggg");
//                button.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
//                return new MyViewHolder(button);
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return 6;
//            }
//
//            class MyViewHolder extends RecyclerView.ViewHolder {
//
//                public MyViewHolder(View itemView) {
//                    super(itemView);
//                }
//            }
//        });
    }
    ArrayList<String> mDatas;
    private void initData() {
        mDatas = new ArrayList<String>();
        for ( int i=0; i < 9; i++) {
            mDatas.add( "item"+i);
        }
    }

}
