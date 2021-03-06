package com.example.sparsh.sudokusolver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Solver extends AppCompatActivity {

    private int grid[][] = new int[9][9];
    private GridLayout gridLayout;
    private TextView tv;
    private ListView lv;
    private Button clickedbtn;
    private int num,pos;

    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //System.out.println("\n\n\n\n\n\nfbefore\n\n\n\n\n\n");
        setContentView(R.layout.activity_solver);
        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        tv = (TextView)findViewById(R.id.tv1);
        //System.out.println("\n\n\n\n\n\nfggfhhgffgcgccfgcf\n\n\n\n\n\n");
        lv = (ListView)findViewById(R.id.lv);
        String[] Nums = {"1","2","3","4","5","6","7","8","9","clear"};
        adapter = new ArrayAdapter<String>(this,R.layout.simplerow,Nums);
        lv.setAdapter(adapter);
        resetGrid();
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("*****handling input onclick*******"+i+" "+l);
                ((Button)view).performClick();
            }




        });*/



        /*for(int i=0;i<81;i++){
            gridbtn[i]=new Button(this);
            gridbtn[i].setId(i*1000);
            int temp=80-i;
            gridbtn[i].setText(Integer.toString(i));
            //gridbtn[i].setMaxWidth(20);
            //gridbtn[i].setMaxHeight(20);
            //gridbtn[i].setPadding(1,1,1,1);
            gridLayout.addView(gridbtn[i]);
            gridbtn[i].setMinimumWidth(20);
            gridbtn[i].setWidth(90);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) gridLayout.getLayoutParams();
            params.width = 200; params.leftMargin = 300; params.topMargin = 300;
            gridbtn[i].setLayoutParams(params);
            gridbtn[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
           // gridLayout.setPadding(1,1,1,1);
            //gridbtn[i].setTextSize(10);


        }*/


    }

    private void resetGrid(){
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                grid[i][j]=0;
            }
        }
    }

    public void resetBtnClicked(View v){
        recreate();
    }

    public void selectItem(View v){
        //System.out.println("********strted selectitem*********"+((Button)v).getText());
        //lv= (ListView)v;
        //System.out.println((((Button)v).getText().toString()));
        //num= (int)lv.getSelectedItem();

        int i=pos/9,j=pos%9;
        Button selectedbtn=(Button)v;
        String sel = selectedbtn.getText().toString();

        try{
            num = Integer.parseInt(sel);
            grid[i][j]=num;
        }catch (NumberFormatException ne){
            grid[i][j]=0;
            clickedbtn.setText("");
            lv.setVisibility(View.GONE);
            tv.setText("Enter Known Numbers");
            gridLayout.setVisibility(View.VISIBLE);
            return;
        }

        // System.out.println("****inside selected *******"+num);
        // checking invalid entry

        int k,l,clashes=0;
        for(k=0;k<9;k++)
            if((grid[i][k]==grid[i][j]) && (j!=k) )
                clashes=1;
        for(k=0;k<9;k++)
            if((grid[k][j]==grid[i][j]) && (i!=k) )
                clashes=1;
        int row=i,col=j;
        while(row%3!=0) row--;
        while(col%3!=0) col--;
        for( k=row;k<row+3;k++)
        {
            for(l=col;l<col+3;l++)
            {
                if(i!=k && j!=l)
                    if(grid[i][j]==grid[k][l])
                        clashes=1;
            }
        }
        if(clashes==1)
        {
            Toast.makeText(this,"Choose another number",Toast.LENGTH_SHORT)
                    .show();
            grid[i][j]=0;
            clickedbtn.setText("");
        }

        else {
            clickedbtn.setText(Integer.toString(num));
            clickedbtn.setTextColor(0xf2f2032f);
        }
        //clickedbtn.setTextColor(0xFF4081);

        lv.setVisibility(View.GONE);
        tv.setText("Enter Known Numbers");
        gridLayout.setVisibility(View.VISIBLE);

    }

    public void cellClicked(View v){
        //System.out.println("****inside cellclicked *******");
        clickedbtn = (Button)v;
        pos=(int)gridLayout.indexOfChild(clickedbtn);
        gridLayout.setVisibility(View.GONE);
        tv.setText("Choose a Number");
        lv.setVisibility(View.VISIBLE);

        //System.out.println("****finshed cellclicked *******"+pos);

        // tv.setText(Integer.toString(pos));


    }

    public void SolveNow(View v){
        if(solve(0)==0) {
            tv.setText("Unsolvable Puzzle");
            resetGrid();
            return;
        }
        for(int i=0;i<81;i++){
            clickedbtn=(Button)gridLayout.getChildAt(i);
            num = grid[i/9][i%9];
            clickedbtn.setText(Integer.toString(num));
        }
        resetGrid();
        tv.setText("Puzzle Solved");

    }
    private int solve(int depth)
    {
        int i,j,k;
        int possb[]= new int[9];
        int temp;
        //System.out.println(depth);
        for(i=0;i<9;i++)
        {
            for(j=0;j<9;j++)
            {
                if(grid[i][j]==0)
                {

                    for(k=0;k<9;k++)
                        possb[k]=k+1;           //k-> for array possb

                    checkrow(i,possb);     //possbsiblites->possbsible values for particular box
                    checkcol(j,possb);
                    checkbox(i,j,possb);
                    for(k=0;k<9;k++)
                    {
                        if(possb[k]!=0)
                        {
                            temp=grid[i][j];
                            grid[i][j]=possb[k];
                            if(solve(depth+1)!=0)         //if grid is solvable with this value of that box(i,j)
                                return 1;
                            else grid[i][j]=temp;

                        }

                    }

                    //no possible value for this box. backtrack!
                    return 0;

                }   // end of if stmnt

                // goes to next box
            }
        }
        return 1;

    } //function ends


    private void checkrow(int row,int[] possb)
    {
        int i;
        for(i=0;i<9;i++)
        {
            if(grid[row][i]!=0)
                possb[grid[row][i]-1]=0;
        }

    }

    private void checkcol(int col,int possb[])
    {
        int i;
        for(i=0;i<9;i++)
        {
            if(grid[i][col]!=0)
                possb[grid[i][col]-1]=0;
        }

    }

    private void checkbox(int row,int col,int possb[])
    {
        int i,j;
        while(row%3!=0) row--;
        while(col%3!=0) col--;
        for( i=row;i<row+3;i++)
        {
            for(j=col;j<col+3;j++)
            {
                if(grid[i][j]!=0)
                    possb[grid[i][j]-1]=0;
            }
        }

    }
}
