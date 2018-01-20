package com.example.wanhao.tasktool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.dialog.PasswordDialog;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.tool.SaveDataUtil;
import com.example.wanhao.tasktool.tool.StringUtil;

public class OptionActivity extends TopBarBaseActivity {
    private Switch swOpenPassWord;
    private boolean isCreat;
    private boolean isCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_option;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        swOpenPassWord =(Switch) findViewById(R.id.ac_option_opencode);

        if(Constant.ISNEEDPASSWORD){
            swOpenPassWord.setChecked(true);
        }

        initEvent();
    }

    private void initEvent() {
        swOpenPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swOpenPassWord.isChecked()){
                    //未设置密码
                    creatPassowrd();
                }else{
                    String token = SaveDataUtil.getValueFromSharedPreferences(OptionActivity.this, Constant.CODE_FILE_NAME,Constant.PASSWORD_CODE);
                    deletePassowrd(StringUtil.stringToIntAr(token));
                }
            }
        });

        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                OptionActivity.this.finish();
            }
        });

        setTopRightButton("还原", new OnClickListener() {
            @Override
            public void onClick() {
                Constant.ISNEEDPASSWORD = false;
                swOpenPassWord.setChecked(false);
                SaveDataUtil.saveToSharedPreferences(OptionActivity.this, Constant.CODE_FILE_NAME,
                        Constant.PASSWORD_CODE,"");
            }
        });
        //清空密码

    }

    private void creatPassowrd(){
        final PasswordDialog firstDialog = new PasswordDialog(OptionActivity.this,"设置密码");
        isCreat = false;

        firstDialog.setOnClickListener(new PasswordDialog.OnClickListener() {
            @Override
            public void onClick() {
                final PasswordDialog enterDialog = new PasswordDialog(OptionActivity.this, "确认输入");
                enterDialog.setOnClickListener(new PasswordDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        if(enterDialog.isAlike(firstDialog.getNums())){
                            //前后密码一致，设置密码
                            SaveDataUtil.saveToSharedPreferences(OptionActivity.this, Constant.CODE_FILE_NAME,
                                    Constant.PASSWORD_CODE, StringUtil.intArToSring(enterDialog.getNums()));
                            Toast.makeText(OptionActivity.this,"一致",Toast.LENGTH_SHORT).show();
                            isCreat = true;
                            enterDialog.cancel();
                        }else{
                            Toast.makeText(OptionActivity.this,"前后密码不一致",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                enterDialog.setOnCancelListener(new PasswordDialog.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        if(isCreat){
                            Constant.ISNEEDPASSWORD =true;
                            swOpenPassWord.setChecked(true);
                        }else{
                            swOpenPassWord.setChecked(false);
                        }
                    }
                });
                enterDialog.show();
                firstDialog.cancel();
            }
        });
        firstDialog.setOnCancelListener(new PasswordDialog.OnCancelListener() {
            @Override
            public void onCancel() {
                if(isCreat){
                    swOpenPassWord.setChecked(true);
                }else{
                    swOpenPassWord.setChecked(false);
                }
            }
        });
        firstDialog.show();

    }

    private void deletePassowrd(final int ar[]){
        final PasswordDialog enterDialog = new PasswordDialog(OptionActivity.this,"输入旧密码");
        isCancel = false;
        enterDialog.setOnClickListener(new PasswordDialog.OnClickListener() {
            @Override
            public void onClick() {
                if(enterDialog.isAlike(ar)){
                    //前后密码一致，设置密码
                    Toast.makeText(OptionActivity.this,"密码消除成功",Toast.LENGTH_SHORT).show();
                    SaveDataUtil.saveToSharedPreferences(OptionActivity.this, Constant.CODE_FILE_NAME,
                            Constant.PASSWORD_CODE,"");
                    SaveDataUtil.saveToSharedPreferences(OptionActivity.this, Constant.TODAY_CHECK_FILE_NAME,
                            Constant.TODAY_CHECK_CODE,"");
                    Constant.ISNEEDPASSWORD =false;
                    isCancel = true;
                    enterDialog.cancel();
                }else{
                    Toast.makeText(OptionActivity.this,"前后密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });
        enterDialog.setOnCancelListener(new PasswordDialog.OnCancelListener() {
            @Override
            public void onCancel() {
                if(isCancel){
                    swOpenPassWord.setChecked(false);
                }else{
                    swOpenPassWord.setChecked(true);
                }
            }
        });
        enterDialog.show();
    }
}
