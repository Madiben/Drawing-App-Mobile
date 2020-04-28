package com.example.drawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private boolean isDrawInit;
    @Override
    protected void onResume(){
        super.onResume();
        if(!isDrawInit)
        {
            initDraw();
            final Button clearButton = findViewById(R.id.clear);
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paintView.clear();
                }
            });
            final TextView brushSize = findViewById(R.id.brushSize);
            brushSize.setText("10");

            final TextView brushColor = findViewById(R.id.brushColor);
            brushColor.setText("Black");
            isDrawInit=true;
            final Button incBrushSize= findViewById(R.id.brushSizeP);
            incBrushSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                paintView.brushSize++;
                brushSize.setText(String.valueOf(paintView.brushSize));
                }
            });
            final Button decBrushSize= findViewById(R.id.brushSizeM);
            decBrushSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(paintView.brushSize>1)
                    {
                        paintView.brushSize--;
                        brushSize.setText(String.valueOf(paintView.brushSize));
                    }
                }
            });

            final Button changeColor = findViewById(R.id.changeColor);
            changeColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(paintView.brushColor == Color.BLACK) {
                      paintView.brushColor = Color.BLUE;
                        brushSize.setText(String.valueOf(paintView.brushColor));
                     } else if(paintView.brushColor == Color.BLUE) {
                        paintView.brushColor = Color.RED;
                        brushSize.setText(String.valueOf(paintView.brushColor));
                    } else if(paintView.brushColor == Color.RED) {
                        paintView.brushColor = Color.GREEN;
                        brushSize.setText(String.valueOf(paintView.brushColor));
                    }else{
                        paintView.brushColor = Color.BLACK;
                        brushSize.setText(String.valueOf(paintView.brushColor));
                      }
                }
            });


        }
    }

    private PaintView paintView;

    private void initDraw(){
        paintView = findViewById(R.id.paintView);
        DisplayMetrics matrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(matrics);
        paintView.init(matrics);
    }

    static class FingerPath{
        int color;
        int strokeWidth;
        Path path;

        FingerPath(int color,int strokeWidth,Path path){
            this.color=color;
            this.strokeWidth=strokeWidth;
            this.path=path;
        }
    }

}
