package com.example.android.gridview.filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * Created by Tanaskovic on 2/13/2018.
 */

public class CustomInputFilter implements InputFilter{

    public void checkQuantityInput(EditText editText){


        InputFilter filter = new InputFilter() {

            final int maxDigitsBeforeDecimalPoint=2;
            final int maxDigitsAfterDecimalPoint=0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[] { filter });
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        return null;
    }
}
