package br.senac.a26_09;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentos = new ArrayList<Fragment>();
    private List<String> titulos = new ArrayList<String>();

    public TabPageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentos.add(new Usuario());
        titulos.add("Usuario");
        fragmentos.add(new Produto());
        titulos.add("Produto");
        fragmentos.add(new Cliente());
        titulos.add("Cliente");
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
