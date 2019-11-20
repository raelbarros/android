package br.senac.navigationviewtest;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.senac.navigationviewtest.Fragmento01;
import br.senac.navigationviewtest.Fragmento02;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentos = new ArrayList<Fragment>();
    private List<String> titulos = new ArrayList<String>();


    public TabPageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentos.add(new Fragmento01());
        titulos.add("Fragmento01");
        fragmentos.add(new Fragmento02());
        titulos.add("Fragmento02");
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentos.get(position);
    }

    @Override
    public int getCount() {
        return fragmentos.size();
    }

    public CharSequence getPageTitle(int position){
        return titulos.get(position);
    }
}
