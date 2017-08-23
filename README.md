# Transitional ImageView

This is an imageView which implement shared element transition pattern on your desired image.
 
 
 ![](https://raw.githubusercontent.com/MostafaAryan/transitional-imageview/master/app/src/main/res/drawable/shoe-app-demo.gif)  
  
  
  
  
## Usage:
#### Step 1

Add JitPack repository in your root build.gradle at the end of repositories.

    allprojects {
        repositories {
    	    ...
    	    maven { url 'https://jitpack.io' }
        }
    }
   
Add dependency in your app level build.gradle.

    dependencies {
        compile 'com.github.MostafaAryan:transitional-imageview:v0.2'
    }

#### Step 2
Create TransitionalImageView in your layout xml file.

    <com.mostafaaryan.transitionalimageview.TransitionalImageView
            android:id="@+id/transitional_image"
            android:layout_width="100dp"
            android:layout_height="wrap_content"
            android:scaleType="fitXY"
            android:adjustViewBounds="true"
            app:res_id="@drawable/sample_image" />
            
#### Step 3
Use builder pattern to build a TransitionalImage object and set object to your TransitionalImageView (previously added to layout xml file).

    TransitionalImageView transitionalImageView = (TransitionalImageView) findViewById(R.id.transitional_image);
    TransitionalImage transitionalImage = new TransitionalImage.Builder()
                                        .duration(500)
                                        .backgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color))
                                        .image(R.drawable.sample_image)
                                        /* or */
                                        .image(bitmap)
                                        .create();
    transitionalImageView.setTransitionalImage(transitionalImage);
    
#### Final step
Nothing really! Just build your app, click on the image and watch the magic happen ;) .

## Customization

You can set image resource id in xml tag:

    app:res_id="@drawable/sample_image"
    
Or set it in builder pattern:

    .image(R.drawable.sample_image)
    
Also you can pass image as a bitmap:

    .image(bitmap)
    
Customization of transitional animation duration & background color for transitioned screen is possible.

    .duration(500)
    .backgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color))


#### Display images using image downloading libraries

##### Picasso
    AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = Picasso.with(MainActivity.this).load(imageUrl).get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /* Build new TransitionalImage using builder pattern and set it to your TransitionalImageView. */
                            }
                        });
                    } catch (IOException e) {e.printStackTrace();}
                }
            });
         
##### Glide
    Glide.with(this).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        /* Build new TransitionalImage using builder pattern and set it to your TransitionalImageView. */
        }
    });
    
##### Universal Image Loader

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DisplayImageOptions dio = new DisplayImageOptions.Builder()
                        .cacheInMemory(false).build();
                final Bitmap bmp = imageLoader.loadImageSync(imageUrl, dio);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /* Build new TransitionalImage using builder pattern and set it to your TransitionalImageView. */                    
                    }
                });
            }
        });
        
        
