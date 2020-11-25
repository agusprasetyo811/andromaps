package com.omapslab.andromaps.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

/**
 * Alert Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class AlertHelper {

    /**
     * Toast Helper
     *
     * @param a
     * @param m
     */
    public void toast(Activity a, String m) {
        Toast.makeText(a, m, Toast.LENGTH_SHORT).show();
    }

    /**
     * DialogNeutralOk Helper
     *
     * @param a
     * @param t
     * @param m
     */
    public void dialogNeutralOK(Activity a, String t, String m) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
            alertDialogBuilder.setTitle(t);
            alertDialogBuilder.setMessage(m).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {

        }
    }

    /**
     * Dialog with prop
     *  @param a
     * @param p
     */
    public AlertDialog dialog(Activity a, DialogProperties p) {
        AlertDialog.Builder alertDialogBuilder;
        if (p.getTheme() == 0) {
            alertDialogBuilder = new AlertDialog.Builder(a);
        } else {
            alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(a, p.getTheme()));
        }
        alertDialogBuilder.setTitle(p.getTitle());
        alertDialogBuilder.setMessage(p.getMsg()).setCancelable(p.isCancelable()).setNeutralButton(p.getBtnOK(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * Dialog with prop and listener
     *  @param a
     * @param p
     * @param listener
     */
    public AlertDialog dialog(Activity a, DialogProperties p, final dialogNeutralListener listener) {
        AlertDialog.Builder alertDialogBuilder;
        if (p.getTheme() == 0) {
            alertDialogBuilder = new AlertDialog.Builder(a);
        } else {
            alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(a, p.getTheme()));
        }
        alertDialogBuilder.setTitle(p.getTitle());
        alertDialogBuilder.setMessage(p.getMsg()).setCancelable(p.isCancelable()).setNeutralButton(p.getBtnOK(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onClicked();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * DialogNeutralOk Helper
     *  @param a
     * @param t
     * @param m
     * @param listener
     */
    public AlertDialog dialogNeutralOK(Activity a, String t, String m, final dialogNeutralListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle(t);
        alertDialogBuilder.setMessage(m).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onClicked();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * DialogConfirm Helper
     *  @param a
     * @param t
     * @param m
     * @param listener
     */
    public AlertDialog dialogConfirm(Activity a, String t, String m, final dialogConfirmListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle(t);
        alertDialogBuilder.setMessage(m).setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onOkClick();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onCancelClick();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * Dialog confirm with prop
     *  @param a
     * @param p
     * @param listener
     */
    public AlertDialog dialogConfirm(Activity a, DialogProperties p, final dialogConfirmListener listener) {
        AlertDialog.Builder alertDialogBuilder = null;
        if (p.getTheme() == 0) {
            alertDialogBuilder = new AlertDialog.Builder(a);
        } else {
            alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(a, p.getTheme()));
        }
        alertDialogBuilder.setTitle(p.getTitle());
        alertDialogBuilder.setMessage(p.getMsg()).setCancelable(p.isCancelable());
        alertDialogBuilder.setPositiveButton(p.getBtnPositive(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onOkClick();
            }
        });
        alertDialogBuilder.setNegativeButton(p.getBtnNegative(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onCancelClick();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }


    /**
     * Interface DialogConfirm
     */
    public interface dialogConfirmListener {
        void onOkClick();

        void onCancelClick();
    }

    /**
     * Interface DialogNeutral
     */
    public interface dialogNeutralListener {
        void onClicked();
    }

    public static class DialogProperties {
        private String btnOK = "OK";
        private String btnPositive = "Yes";
        private String btnNegative = "No";
        private String title = "";
        private String msg = "";
        private int theme = 0;
        private boolean cancelable = false;

        public String getBtnOK() {
            return btnOK;
        }

        public DialogProperties setBtnOK(String btnOK) {
            this.btnOK = btnOK;
            return this;
        }

        public String getBtnPositive() {
            return btnPositive;
        }

        public DialogProperties setBtnPositive(String btnPositive) {
            this.btnPositive = btnPositive;
            return this;
        }

        public String getBtnNegative() {
            return btnNegative;
        }

        public DialogProperties setBtnNegative(String btnNegative) {
            this.btnNegative = btnNegative;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public DialogProperties setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public DialogProperties setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public int getTheme() {
            return theme;
        }

        public DialogProperties setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public DialogProperties setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }
    }


}
