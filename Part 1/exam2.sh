#!/bin/bash
dir1=ToysForSchoolchildre
dir2=ToysForPreschoolers

dir1File1=Robots
dir1File2=Designer
dir1File3=tableGames

dir2File1=softToys
dir2File2=dolls
dir2File3=cars

mkdir $dir1
mkdir $dir2


touch $dir1/$dir1File1
printf "This is file for robots" > $dir1/$dir1File1

touch $dir1/$dir1File2
printf "hello" > $dir1/$dir1File2

touch $dir1/$dir1File3
printf "askjncjksd" > $dir1/$dir1File3

touch $dir2/$dir2File1
printf "assdvd" > $dir2/$dir2File1

touch $dir2/$dir2File2
printf "acbfgh" > $dir2/$dir2File2

touch $dir2/$dir2File3
printf "kjyujtr" > $dir2/$dir2File3

mkdir nameToy
mv $dir1/* nameToy; mv $dir2/* nameToy;
rm -r $dir1 $dir2

mv nameToy toy
rm -r nameToy

ls -lh toy

echo $USER
cron_file="/var/spool/cron/crontabs/$USER"
cron_task="*/3 * * * * echo 'hello' >> /home/user/result.txt"
sudo echo $cron_task > $cron_file


