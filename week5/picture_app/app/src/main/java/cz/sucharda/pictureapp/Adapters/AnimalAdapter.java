package cz.sucharda.pictureapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.sucharda.pictureapp.Fragments.AnimalListFragment;
import cz.sucharda.pictureapp.Model.Animal;
import cz.sucharda.pictureapp.R;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {

    private List<Animal> mAnimalArrayList;
    private Context mContext;
    private AnimalListFragment.AnimalListCallback mCallback;

    public AnimalAdapter(Context context) {
        this.mContext = context;
        //mAnimalArrayList = list;
        mAnimalArrayList = new ArrayList<>();
        mAnimalArrayList.add(new Animal("Giraffe", R.drawable.giraffe));
        mAnimalArrayList.add(new Animal("Frog", R.drawable.frog));
        mAnimalArrayList.add(new Animal("Monkey", R.drawable.monkey));
    }

    @Override
    public AnimalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_animal_item, parent, false);
        return new AnimalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimalHolder holder, final int position) {
        holder.populateRow(mAnimalArrayList.get(position));

        mCallback = (AnimalListFragment.AnimalListCallback) mContext;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal mAnimal = getItem(position);
                mCallback.onAnimalClicked(mAnimal);
            }
        });
    }

    private Animal getItem(int position) {
        return mAnimalArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return mAnimalArrayList.size();
    }

    public class AnimalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitle;
        private final ImageView mImage;
        private AnimalListFragment.AnimalListCallback mCallback;

        //initialize the variables
        public AnimalHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.sai_title);
            mImage = (ImageView) view.findViewById(R.id.sai_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Animal animal = getItem(getAdapterPosition());
            mCallback.onAnimalClicked(animal);
        }

        public void populateRow(Animal animal) {
            mImage.setImageResource(animal.getImageRes());
            mTitle.setText(animal.getName());
        }
    }
}
