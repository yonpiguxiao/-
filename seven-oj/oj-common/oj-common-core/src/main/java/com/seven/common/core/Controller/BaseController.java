package com.seven.common.core.Controller;

import com.seven.common.core.domain.R;


public class BaseController {
    public R<Void> toR(int rows) {
        return rows > 0 ? R.ok() : R.fail();
    }

    public R<Void> toR(boolean result) {
        return result ? R.ok() : R.fail();
    }
}
