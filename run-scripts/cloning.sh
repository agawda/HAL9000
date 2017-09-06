#!/usr/bin/env bash
echo "Cloning the repository" &
git clone https://github.com/agawda/HAL9000
echo "Repository cloned"
echo "Switching to master"
cd HAL9000
git checkout master
git branch