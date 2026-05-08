package cn.roidlin.bookkeepingbook.ui.common;

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
    * 瀹屾垚浜嬩欢
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
        this.editText.setInputType(InputType.TYPE_NULL);    //鍙栨秷寮瑰嚭绯荤粺閿洏
        k1 = new Keyboard(this.editText.getContext(),R.xml.key);

        this.keyboardView.setKeyboard(k1);                               //璁剧疆瑕佹樉绀洪敭鐩樼殑鏍峰紡
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
                //闇€瑕侀€氳繃璇ユ柟娉曞垽鏂摢涓鐐瑰嚮
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                switch(primaryCode){
                    //鐐瑰嚮鍒犻櫎閿?
                    case Keyboard.KEYCODE_DELETE:
                    if (editable != null &&editable.length()>0){
                        if (start>0) {
                            editable.delete(start - 1, start);
                        }
                    }
                        break;
                    //鐐瑰嚮娓呴浂
                    case Keyboard.KEYCODE_CANCEL:
                        editable.clear();
                        break;
                    //鐐瑰嚮瀹屾垚
                    case Keyboard.KEYCODE_DONE:
                    //閫氳繃鎺ュ彛鍥炶皟鐨勬柟娉曪紝褰撶偣鍑荤‘瀹氭椂锛屽彲浠ヨ皟鐢ㄨ繖涓柟娉?
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

    //鏄剧ず閿洏
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //闅愯棌閿洏
    public void  hideKeyboard(){
        int visivility = keyboardView.getVisibility();
        if (visivility == View.VISIBLE  || visivility == View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }


}

