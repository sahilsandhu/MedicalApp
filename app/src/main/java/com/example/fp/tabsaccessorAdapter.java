package com.example.fp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabsaccessorAdapter extends FragmentPagerAdapter {

    public tabsaccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position)
       {
           case 0:
               MainFragment mainFragment = new MainFragment();
               return mainFragment;

           case 1:
               ChatFragment chatFragment = new ChatFragment();
               return chatFragment;
           case 2:
               ProfileFragment profileFragment = new ProfileFragment();
               return profileFragment;
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);

        switch (position)
        {
            case 0:
               return "MEDSCAN";
            case 1:
              return "CHATS";
            case 2:
                return "PROFILE";
            default:
                return null;
        }
    }
}
