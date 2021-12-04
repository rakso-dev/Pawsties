package com.pawsties.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button sigin, signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_form);

        sigin = findViewById(R.id.btnSignin);
        signup = findViewById(R.id.btnSignup);

        sigin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
            startActivity(intent);
        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    /*public static final int FILE_CHOOSER_REQUEST_CODE = 4001;
    ImageView ivCanvas;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_page);

        Toolbar toolbar = findViewById (R.id.toolbar);
        setActionBar (Objects.requireNonNull (toolbar));
        toolbar.setTitle (R.string.app_name);

        ivCanvas = findViewById (R.id.ivCanvas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate (R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        int id = item.getItemId ();
        if (id == R.id.mnuOpen) {
            openDialog ();
        } else if (id == R.id.mnuSepia) {
            convertSephia2 ();
        } else if (id == R.id.mnuGrayScale) {
            convertMonochrome ();
        } else if (id == R.id.mnuNegative) {
            convertNegative ();
        }

        return super.onOptionsItemSelected(item);

    }

    private void openDialog () {
        Intent intent = new Intent ();
        intent.setType ("image/jpeg");
        intent.setAction (Intent.ACTION_GET_CONTENT);

        startActivityForResult (Intent.createChooser (intent, "Select image file"), FILE_CHOOSER_REQUEST_CODE);
    }

    private void convertSephia () {
        final Bitmap bitmap = getBitmapFromDrawable (ivCanvas.getDrawable ());

        ExecutorService executorService = Executors.newFixedThreadPool (1);
        Future<Bitmap> bitmapFuture = executorService.submit (new SephiaCallable (bitmap));
        executorService.shutdown ();

        // do other work

//        if (bitmapFuture.isDone ()) {
//            try {
//                ivCanvas.setImageBitmap (bitmapFuture.get ()); // .get puede bloquear la UI
//            } catch (ExecutionException | InterruptedException ex) {
//                ex.printStackTrace ();
//            }
//        }

        // se obtiene la referencia al objeto handler del hilo principal, lo que permite enviar
        // mensajes y tareas a la cola de mensaje de dicho hilo.
        Handler myHandler = getWindow().getDecorView().getHandler ();

        // para verificar el estado de la tarea, se utiliza un hilo adicional de modo que no
        // se bloquee la interfaz de usuario.
        new Thread (() -> {
            while (true) {
                Log.d ("TYAM", "Checking if future task has finish...");
                if (bitmapFuture.isDone ()) {
                    // se utiliza la función post del handler obtenido para colocar un objeto runnable
                    // que será ejecutado dentro del loop del hilo principal, donde se tiene
                    // acceso a los controles de la interfaz de usuario.
                    myHandler.post (() -> {
                        try {
                            Bitmap bmp = bitmapFuture.get (); // bloqueante
                            ivCanvas.setImageBitmap (bmp);
                        } catch (ExecutionException | InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                    });

                    break;
                }
            }
        }).start ();
    }

    private void convertSephia2 () {
        final Bitmap bitmap = getBitmapFromDrawable (ivCanvas.getDrawable ());

        ExecutorService executorService = Executors.newFixedThreadPool (1);
        MyAsync<Bitmap> myAsync = new MyAsync<>(new SephiaCallable (bitmap));
        myAsync.setTaskDoneListener (() -> {
            try {
                Bitmap bmp = myAsync.get (); // bloqueante

                this.runOnUiThread (() -> {
                    ivCanvas.setImageBitmap (bmp);
                });
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace ();
            }
        });

        executorService.submit (myAsync);
        executorService.shutdown ();
    }

    private void convertMonochrome () {
        Bitmap bitmap = getBitmapFromDrawable (ivCanvas.getDrawable ());

        Thread thread = new Thread (() -> {
            Bitmap bmp = toGrayscale (bitmap);

            // cambio de contexto
            this.runOnUiThread (() -> {
                ivCanvas.setImageBitmap (bmp);
            });
        });

        thread.start ();
    }

    public Bitmap toGrayscale (Bitmap src) {
        float [] matrix = new float [] {
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0, 0, 0, 1, 0, };

        Bitmap dest = Bitmap.createBitmap (
                src.getWidth (),
                src.getHeight (),
                src.getConfig ());

        Canvas canvas = new Canvas (dest);
        Paint paint = new Paint ();
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter (matrix);
        paint.setColorFilter (filter);
        canvas.drawBitmap (src, 0, 0, paint);

        return dest;
    }

    private void convertNegative () {
        Bitmap bitmap = getBitmapFromDrawable (ivCanvas.getDrawable ());
        NegativeFilterAsyncTask task = new NegativeFilterAsyncTask (ivCanvas);
        task.execute (bitmap);
    }

    /**
     * Obtiene un objeto de mapa de bits a partir del objeto Drawable (canvas) recibido.
     *
     * @param drble Drawable que contiene la imagen deseada.
     * @return objeto de mapa de bits con la estructura de la imagen.
     */
/*    private Bitmap getBitmapFromDrawable (Drawable drble) {
        // debido a la forma que el sistema dibuja una imagen en un el sistema gráfico
        // es necearios realzar comprobaciones para saber del tipo de objeto Drawable
        // con que se está trabajando.
        //
        // si el objeto recibido es del tipo BitmapDrawable no se requieren más conversiones
        if (drble instanceof BitmapDrawable) {
            return  ((BitmapDrawable) drble).getBitmap ();
        }

        // en caso contrario, se crea un nuevo objeto Bitmap a partir del contenido
        // del objeto Drawable
        Bitmap bitmap = Bitmap.createBitmap (drble.getIntrinsicWidth (), drble.getIntrinsicHeight (), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drble.setBounds (0, 0, canvas.getWidth (), canvas.getHeight ());
        drble.draw (canvas);

        return bitmap;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            Uri selectedImage = data.getData ();
            ivCanvas.setImageURI (selectedImage);
        }
    } */
}
