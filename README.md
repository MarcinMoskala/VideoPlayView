# VideoPlayerView

Custom Android view with video player, loader and placeholder image.

[![](https://jitpack.io/v/marcinmoskala/VideoPlayView.svg)](https://jitpack.io/#marcinmoskala/VideoPlayView)
[![Analytics](https://ga-beacon.appspot.com/UA-92159206-4/main-page?pixel)](https://github.com/MarcinMoskala/VideoPlayView)
[![Analytics](https://ga-beacon.appspot.com/UA-92159206-7/main-page?pixel)](https://github.com/MarcinMoskala/VideoPlayView)


To stay up-to-date with news about the library [![Twitter URL](https://img.shields.io/twitter/url/https/twitter.com/fold_left.svg?style=social&label=Follow%20%40marcinmoskala)](https://twitter.com/marcinmoskala?ref_src=twsrc%5Etfw)

## Usage

Here is an example flow:

![Gif example](art/flow.gif)

[Here](https://github.com/MarcinMoskala/VideoPlayView/blob/master/app/src/main/res/layout/activity_sample.xml) you can view all examples visible on gif.

When you use `loop` then video will be looping forever:

![Img1](art/loop.gif)

```xml
<com.marcinmoskala.videoplayview.VideoPlayView
    android:layout_width="match_parent"
    android:layout_height="200dp"
    app:loop="true"
    app:videoUrl="https://github.com/MarcinMoskala/VideoPlayView/raw/master/videos/cat1.mp4" />
```

You can set custom loader for the time when movie is downloaded from URL:

![Img1](art/loading.gif)

```xml
<com.marcinmoskala.videoplayview.VideoPlayView
    android:id="@+id/bigVideoView"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    app:videoUrl="https://github.com/MarcinMoskala/VideoPlayView/raw/master/videos/gdg.mp4" />
```

Static images can be set in xml, as `loadingButton`, but to display gif we need to use some library, like Glide:

```java
VideoPlayView bigVideoView = findViewById(R.id.bigVideoView);
Glide.with(this)
        .load(R.drawable.loader)
        .into(bigVideoView.getLoadingView());
```

You can also show preview images. What is more, videos can be paused by user and preview image is shown then:

![Img1](art/pause.gif)

```xml
<com.marcinmoskala.videoplayview.VideoPlayView
    android:id="@+id/picassoVideoView"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    app:loop="true"
    app:videoUrl="https://github.com/MarcinMoskala/VideoPlayView/raw/master/videos/cat1.mp4" />
```

Image for loading and for pause can be defined in xml as `image` or it can be set programmatically. Then we can use libraries, like Picasso, to load it:

```java
VideoPlayView videoView = findViewById(R.id.picassoVideoView);
Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(videoView.getImageView());
```

## Customization

VideoPlayerView properties are:
* `image` - reference to preview image.
* `playButton` - reference to play button image.
* `loadingButton` - reference to loader image.
* `videoUrl` - video url.
* `playButton` - max vale of progress (100 by default).
* `loop` - video will be replayed automatically when it is finished (`false` by default).
* `autoplay` - video will be played automatically when it is loaded (`false` by default).

## Installation

Just add in your module `build.gradle` following dependency:

```groovy
dependencies {
    compile 'com.github.marcinmoskala:VideoPlayView:0.0.2'
}
```

Also add on your module `build.gradle` (unless you already have it):

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

License
-------

    Copyright 2017 Marcin Moskała

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
