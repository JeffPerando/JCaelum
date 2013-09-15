#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=MinGW-Windows
CND_DLIB_EXT=dll
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/348483528/EnumColor.o \
	${OBJECTDIR}/_ext/348483528/Model.o \
	${OBJECTDIR}/_ext/348483528/Tuple.o \
	${OBJECTDIR}/_ext/348483528/Vector2.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a
	${AR} -rv ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a ${OBJECTFILES} 
	$(RANLIB) ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a

${OBJECTDIR}/_ext/348483528/EnumColor.o: nbproject/Makefile-${CND_CONF}.mk /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/EnumColor.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/348483528
	$(COMPILE.cc) -g -I/C/MinGW/include -o ${OBJECTDIR}/_ext/348483528/EnumColor.o /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/EnumColor.cpp

${OBJECTDIR}/_ext/348483528/Model.o: nbproject/Makefile-${CND_CONF}.mk /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Model.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/348483528
	$(COMPILE.cc) -g -I/C/MinGW/include -o ${OBJECTDIR}/_ext/348483528/Model.o /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Model.cpp

${OBJECTDIR}/_ext/348483528/Tuple.o: nbproject/Makefile-${CND_CONF}.mk /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Tuple.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/348483528
	$(COMPILE.cc) -g -I/C/MinGW/include -o ${OBJECTDIR}/_ext/348483528/Tuple.o /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Tuple.cpp

${OBJECTDIR}/_ext/348483528/Vector2.o: nbproject/Makefile-${CND_CONF}.mk /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Vector2.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/348483528
	$(COMPILE.cc) -g -I/C/MinGW/include -o ${OBJECTDIR}/_ext/348483528/Vector2.o /C/Users/Game_Boy/Realms-of-Caelum/cpp/HawksEngine/Vector2.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/libhawksengine.a

# Subprojects
.clean-subprojects:
