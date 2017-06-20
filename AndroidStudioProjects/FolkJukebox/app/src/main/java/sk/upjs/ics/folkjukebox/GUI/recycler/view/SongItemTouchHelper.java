package sk.upjs.ics.folkjukebox.GUI.recycler.view;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SongItemTouchHelper extends ItemTouchHelper {

    public SongItemTouchHelper(RecyclerView view, Callback callback) {
        super(callback);
        attachToRecyclerView(view);
    }

    public static class SongCallback extends SimpleCallback{

        private SimpleCursorRecyclerAdapter adapter;

        public SongCallback(SimpleCursorRecyclerAdapter adapter) {
            super(0, RIGHT | LEFT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // nepodporujeme
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.remove(viewHolder.getAdapterPosition());
        }
    }
}
