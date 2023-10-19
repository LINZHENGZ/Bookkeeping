package utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import cn.roidlin.bookkeepingbook.R;

public class KeyBoardUtils {
    private final Keyboard k1;
    private KeyboardView keyboardView;
    private EditText editText;

    /*
    * 完成事件
    * */
    public interface  OnEnsureListener{
         void onEnsure();
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public  KeyBoardUtils(KeyboardView keyboardView, EditText editText){
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);    //取消弹出系统键盘
        k1 = new Keyboard(this.editText.getContext(),R.xml.key);

        this.keyboardView.setKeyboard(k1);                               //设置要显示键盘的样式
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }
        KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                //需要通过该方法判断哪个被点击
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                switch(primaryCode){
                    //点击删除键
                    case Keyboard.KEYCODE_DELETE:
                    if (editable != null &&editable.length()>0){
                        if (start>0) {
                            editable.delete(start - 1, start);
                        }
                    }
                        break;
                    //点击清零
                    case Keyboard.KEYCODE_CANCEL:
                        editable.clear();
                        break;
                    //点击完成
                    case Keyboard.KEYCODE_DONE:
                    //通过接口回调的方法，当点击确定时，可以调用这个方法
                    onEnsureListener.onEnsure();

                        break;
                    default:
                        editable.insert(start,Character.toString((char)primaryCode));
                        break;

                }
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };

    //显示键盘
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //隐藏键盘
    public void  hideKeyboard(){
        int visivility = keyboardView.getVisibility();
        if (visivility == View.VISIBLE  || visivility == View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }


}
