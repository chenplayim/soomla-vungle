#!/usr/bin/python

from mod_pbxproj import *
from os import path, listdir
from shutil import copytree
import sys

frameworks = [
  'usr/lib/libz.dylib',
  'usr/lib/libsqlite3.0.dylib',
  'System/Library/Frameworks/AdSupport.framework',
  'System/Library/Frameworks/StoreKit.framework',
  'System/Library/Frameworks/SystemConfiguration.framework'
]

weak_frameworks = [

]

vungle_framework_dir = path.abspath(path.join(sys.argv[0],'..','..', '..', 'Resources','Plugins','Vungle'))
vungle_framework = path.abspath(path.join(vungle_framework_dir, 'VungleSDK.embeddedframework'))

for root, dirs, files in os.walk(vungle_framework):
    for currentFile in files:
        if currentFile.lower().endswith('.meta'):
            os.remove(os.path.join(root, currentFile))

# filelist = [ f for f in os.listdir(vungle_framework) if f.endswith(".meta") ]
# for f in filelist:
#     os.remove(path.join(vungle_framework, f))

pbx_file_path = sys.argv[1] + '/Unity-iPhone.xcodeproj/project.pbxproj'
pbx_object = XcodeProject.Load(pbx_file_path)

pbx_object.add_framework_search_paths('$(PROJECT_DIR)/VungleSDK.embeddedframework')
# pbx_object.add_header_search_paths([path.abspath(vungle_framework)])
pbx_object.add_folder(vungle_framework)

for framework in frameworks:
  pbx_object.add_file_if_doesnt_exist(framework, tree='SDKROOT')

for framework in weak_frameworks:
  pbx_object.add_file_if_doesnt_exist(framework, tree='SDKROOT', weak=True)

pbx_object.add_other_ldflags('-ObjC')

pbx_object.save()
