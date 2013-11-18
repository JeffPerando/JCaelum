
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.PointerBuffer;
import org.lwjgl.PointerWrapper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GLSync;
import org.lwjgl.opengl.KHRDebugCallback;

/**
 * 
 * A wrapper for modern OpenGL functions.
 * <p>
 * NOTICE: By "modern", I mean only the functions found here: http://www.opengl.org/sdk/docs/man/
 * <p>
 * @author Elusivehawk
 */
public final class GL
{
	private GL(){}
	
	//Begin Caelum Engine exclusive functionality.
	
	public static final int VERTEX_OFFSET = 0;
	public static final int COLOR_OFFSET = 3;
	public static final int TEXCOORD_OFFSET = 7;
	
	private static final List<IGLCleanable> OBJECTS = new ArrayList<IGLCleanable>();
	
	public static void register(IGLCleanable gl)
	{
		OBJECTS.add(gl);
		
	}
	
	public static void cleanup()
	{
		if (!OBJECTS.isEmpty())
		{
			return;
		}
		
		for (IGLCleanable gl : OBJECTS)
		{
			gl.glDelete();
			
		}
		
		OBJECTS.clear();
		
	}
	
	//End Caelum engine exclusive functionality.
	
	//XXX #
	
	public static final int GL_2_BYTES = GL11.GL_2_BYTES;
	public static final int GL_2D = GL11.GL_2D;
	public static final int GL_3_BYTES = GL11.GL_3_BYTES;
	public static final int GL_3D = GL11.GL_3D;
	public static final int GL_3D_COLOR = GL11.GL_3D_COLOR;
	public static final int GL_3D_COLOR_TEXTURE = GL11.GL_3D_COLOR_TEXTURE;
	public static final int GL_4_BYTES = GL11.GL_4_BYTES;
	public static final int GL_4D_COLOR_TEXTURE = GL11.GL_4D_COLOR_TEXTURE;
	
	//XXX A
	
	public static final int GL_ACCUM = GL11.GL_ACCUM;
	public static final int GL_ACCUM_ALPHA_BITS = GL11.GL_ACCUM_ALPHA_BITS;
	public static final int GL_ACCUM_BLUE_BITS = GL11.GL_ACCUM_BLUE_BITS;
	public static final int GL_ACCUM_BUFFER_BIT = GL11.GL_ACCUM_BUFFER_BIT;
	public static final int GL_ACCUM_CLEAR_VALUE = GL11.GL_ACCUM_CLEAR_VALUE;
	public static final int GL_ACCUM_GREEN_BITS = GL11.GL_ACCUM_GREEN_BITS;
	public static final int GL_ACCUM_RED_BITS = GL11.GL_ACCUM_RED_BITS;
	public static final int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = GL42.GL_ACTIVE_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH;
	public static final int GL_ACTIVE_ATTRIBUTES = GL20.GL_ACTIVE_ATTRIBUTES;
	public static final int GL_ACTIVE_PROGRAM = GL41.GL_ACTIVE_PROGRAM;
	public static final int GL_ACTIVE_RESOURCES = GL43.GL_ACTIVE_RESOURCES;
	public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = GL40.GL_ACTIVE_SUBROUTINE_MAX_LENGTH;
	public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = GL40.GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS;
	public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = GL40.GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH;
	public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = GL40.GL_ACTIVE_SUBROUTINE_UNIFORMS;
	public static final int GL_ACTIVE_SUBROUTINES = GL40.GL_ACTIVE_SUBROUTINES;
	public static final int GL_ACTIVE_TEXTURE = GL13.GL_ACTIVE_TEXTURE;
	public static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = GL31.GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH;
	public static final int GL_ACTIVE_UNIFORM_BLOCKS = GL31.GL_ACTIVE_UNIFORM_BLOCKS;
	public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH;
	public static final int GL_ACTIVE_UNIFORMS = GL20.GL_ACTIVE_UNIFORMS;
	public static final int GL_ACTIVE_VARIABLES = GL43.GL_ACTIVE_VARIABLES;
	public static final int GL_ADD = GL11.GL_ADD;
	public static final int GL_ADD_SIGNED = GL13.GL_ADD_SIGNED;
	public static final int GL_ALIASED_LINE_WIDTH_RANGE = GL12.GL_ALIASED_LINE_WIDTH_RANGE;
	public static final int GL_ALIASED_POINT_SIZE_RANGE = GL12.GL_ALIASED_POINT_SIZE_RANGE;
	public static final int GL_ALL_ATTRIB_BITS = GL11.GL_ALL_ATTRIB_BITS;
	public static final int GL_ALL_BARRIER_BITS = GL42.GL_ALL_BARRIER_BITS;
	public static final int GL_ALL_CLIENT_ATTRIB_BITS = GL11.GL_ALL_CLIENT_ATTRIB_BITS;
	public static final int GL_ALL_SHADER_BITS = GL41.GL_ALL_SHADER_BITS;
	public static final int GL_ALPHA = GL11.GL_ALPHA;
	public static final int GL_ALPHA_BIAS = GL11.GL_ALPHA_BIAS;
	public static final int GL_ALPHA_BITS = GL11.GL_ALPHA_BITS;
	public static final int GL_ALPHA_INTEGER = GL30.GL_ALPHA_INTEGER;
	public static final int GL_ALPHA_SCALE = GL11.GL_ALPHA_SCALE;
	public static final int GL_ALPHA_TEST = GL11.GL_ALPHA_TEST;
	public static final int GL_ALPHA_TEST_FUNC = GL11.GL_ALPHA_TEST_FUNC;
	public static final int GL_ALPHA_TEST_REF = GL11.GL_ALPHA_TEST_REF;
	public static final int GL_ALPHA12 = GL11.GL_ALPHA12;
	public static final int GL_ALPHA16 = GL11.GL_ALPHA16;
	public static final int GL_ALPHA16F = GL30.GL_ALPHA16F;
	public static final int GL_ALPHA16I = GL30.GL_ALPHA16I;
	public static final int GL_ALPHA16UI = GL30.GL_ALPHA16UI;
	public static final int GL_ALPHA32F = GL30.GL_ALPHA32F;
	public static final int GL_ALPHA32I = GL30.GL_ALPHA32I;
	public static final int GL_ALPHA32UI = GL30.GL_ALPHA32UI;
	public static final int GL_ALPHA4 = GL11.GL_ALPHA4;
	public static final int GL_ALPHA8 = GL11.GL_ALPHA8;
	public static final int GL_ALPHA8I = GL30.GL_ALPHA8I;
	public static final int GL_ALPHA8UI = GL30.GL_ALPHA8UI;
	public static final int GL_ALREADY_SIGNALED = GL32.GL_ALREADY_SIGNALED;
	public static final int GL_ALWAYS = GL11.GL_ALWAYS;
	public static final int GL_AMBIENT = GL11.GL_AMBIENT;
	public static final int GL_AMBIENT_AND_DIFFUSE = GL11.GL_AMBIENT_AND_DIFFUSE;
	public static final int GL_AND = GL11.GL_AND;
	public static final int GL_AND_INVERTED = GL11.GL_AND_INVERTED;
	public static final int GL_AND_REVERSE = GL11.GL_AND_REVERSE;
	public static final int GL_ANY_SAMPLES_PASSED = GL33.GL_ANY_SAMPLES_PASSED;
	public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = GL43.GL_ANY_SAMPLES_PASSED_CONSERVATIVE;
	public static final int GL_ARRAY_BUFFER = GL15.GL_ARRAY_BUFFER;
	public static final int GL_ARRAY_BUFFER_BINDING = GL15.GL_ARRAY_BUFFER_BINDING;
	public static final int GL_ARRAY_SIZE = GL43.GL_ARRAY_SIZE;
	public static final int GL_ARRAY_STRIDE = GL43.GL_ARRAY_STRIDE;
	public static final int GL_ATOMIC_COUNTER_BARRIER_BIT = GL42.GL_ATOMIC_COUNTER_BARRIER_BIT;
	public static final int GL_ATOMIC_COUNTER_BUFFER = GL42.GL_ATOMIC_COUNTER_BUFFER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = GL42.GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES;
	public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = GL42.GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS;
	public static final int GL_ATOMIC_COUNTER_BUFFER_BINDING = GL42.GL_ATOMIC_COUNTER_BUFFER_BINDING;
	public static final int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = GL42.GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE;
	public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = GL43.GL_ATOMIC_COUNTER_BUFFER_INDEX;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = GL43.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = GL42.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = GL42.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = GL42.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = GL42.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = GL42.GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER;
	public static final int GL_ATOMIC_COUNTER_BUFFER_SIZE = GL42.GL_ATOMIC_COUNTER_BUFFER_SIZE;
	public static final int GL_ATOMIC_COUNTER_BUFFER_START = GL42.GL_ATOMIC_COUNTER_BUFFER_START;
	public static final int GL_ATTACHED_SHADERS = GL20.GL_ATTACHED_SHADERS;
	public static final int GL_ATTRIB_STACK_DEPTH = GL11.GL_ATTRIB_STACK_DEPTH;
	public static final int GL_AUTO_GENERATE_MIPMAP = GL43.GL_AUTO_GENERATE_MIPMAP;
	public static final int GL_AUTO_NORMAL = GL11.GL_AUTO_NORMAL;
	public static final int GL_AUX_BUFFERS = GL11.GL_AUX_BUFFERS;
	public static final int GL_AUX0 = GL11.GL_AUX0;
	public static final int GL_AUX1 = GL11.GL_AUX1;
	public static final int GL_AUX2 = GL11.GL_AUX2;
	public static final int GL_AUX3 = GL11.GL_AUX3;
	
	//XXX B
	
	public static final int GL_BACK = GL11.GL_BACK;
	public static final int GL_BACK_LEFT = GL11.GL_BACK_LEFT;
	public static final int GL_BACK_RIGHT = GL11.GL_BACK_RIGHT;
	public static final int GL_BGR = GL12.GL_BGR;
	public static final int GL_BGR_INTEGER = GL30.GL_BGR_INTEGER;
	public static final int GL_BGRA = GL12.GL_BGRA;
	public static final int GL_BGRA_INTEGER = GL30.GL_BGRA_INTEGER;
	public static final int GL_BITMAP = GL11.GL_BITMAP;
	public static final int GL_BITMAP_TOKEN = GL11.GL_BITMAP_TOKEN;
	public static final int GL_BLEND = GL11.GL_BLEND;
	public static final int GL_BLEND_COLOR = GL14.GL_BLEND_COLOR;
	public static final int GL_BLEND_DST = GL11.GL_BLEND_DST;
	public static final int GL_BLEND_DST_ALPHA = GL14.GL_BLEND_DST_ALPHA;
	public static final int GL_BLEND_DST_RGB = GL14.GL_BLEND_DST_RGB;
	public static final int GL_BLEND_EQUATION = GL14.GL_BLEND_EQUATION;
	public static final int GL_BLEND_EQUATION_ALPHA = GL20.GL_BLEND_EQUATION_ALPHA;
	public static final int GL_BLEND_EQUATION_RGB = GL20.GL_BLEND_EQUATION_RGB;
	public static final int GL_BLEND_SRC = GL11.GL_BLEND_SRC;
	public static final int GL_BLEND_SRC_ALPHA = GL14.GL_BLEND_SRC_ALPHA;
	public static final int GL_BLEND_SRC_RGB = GL14.GL_BLEND_SRC_RGB;
	public static final int GL_BLOCK_INDEX = GL43.GL_BLOCK_INDEX;
	public static final int GL_BLUE = GL11.GL_BLUE;
	public static final int GL_BLUE_BIAS = GL11.GL_BLUE_BIAS;
	public static final int GL_BLUE_BITS = GL11.GL_BLUE_BITS;
	public static final int GL_BLUE_INTEGER = GL30.GL_BLUE_INTEGER;
	public static final int GL_BLUE_SCALE = GL11.GL_BLUE_SCALE;
	public static final int GL_BOOL = GL20.GL_BOOL;
	public static final int GL_BOOL_VEC2 = GL20.GL_BOOL_VEC2;
	public static final int GL_BOOL_VEC3 = GL20.GL_BOOL_VEC3;
	public static final int GL_BOOL_VEC4 = GL20.GL_BOOL_VEC4;
	public static final int GL_BUFFER = GL43.GL_BUFFER;
	public static final int GL_BUFFER_ACCESS = GL15.GL_BUFFER_ACCESS;
	public static final int GL_BUFFER_ACCESS_FLAGS = GL30.GL_BUFFER_ACCESS_FLAGS;
	public static final int GL_BUFFER_BINDING = GL43.GL_BUFFER_BINDING;
	public static final int GL_BUFFER_DATA_SIZE = GL43.GL_BUFFER_DATA_SIZE;
	public static final int GL_BUFFER_IMMUTABLE_STORAGE = GL44.GL_BUFFER_IMMUTABLE_STORAGE;
	public static final int GL_BUFFER_MAP_LENGTH = GL30.GL_BUFFER_MAP_LENGTH;
	public static final int GL_BUFFER_MAP_OFFSET = GL30.GL_BUFFER_MAP_OFFSET;
	public static final int GL_BUFFER_MAP_POINTER = GL15.GL_BUFFER_MAP_POINTER;
	public static final int GL_BUFFER_MAPPED = GL15.GL_BUFFER_MAPPED;
	public static final int GL_BUFFER_SIZE = GL15.GL_BUFFER_SIZE;
	public static final int GL_BUFFER_STORAGE_FLAGS = GL44.GL_BUFFER_STORAGE_FLAGS;
	public static final int GL_BUFFER_UPDATE_BARRIER_BIT = GL42.GL_BUFFER_UPDATE_BARRIER_BIT;
	public static final int GL_BUFFER_USAGE = GL15.GL_BUFFER_USAGE;
	public static final int GL_BUFFER_VARIABLE = GL43.GL_BUFFER_VARIABLE;
	public static final int GL_BYTE = GL11.GL_BYTE;
	
	//XXX C
	
	public static final int GL_C3F_V3F = GL11.GL_C3F_V3F;
	public static final int GL_C4F_N3F_V3F = GL11.GL_C4F_N3F_V3F;
	public static final int GL_C4UB_V2F = GL11.GL_C4UB_V2F;
	public static final int GL_C4UB_V3F = GL11.GL_C4UB_V3F;
	public static final int GL_CAVEAT_SUPPORT = GL43.GL_CAVEAT_SUPPORT;
	public static final int GL_CCW = GL11.GL_CCW;
	public static final int GL_CLAMP = GL11.GL_CLAMP;
	public static final int GL_CLAMP_FRAGMENT_COLOR = GL30.GL_CLAMP_FRAGMENT_COLOR;
	public static final int GL_CLAMP_READ_COLOR = GL30.GL_CLAMP_READ_COLOR;
	public static final int GL_CLAMP_TO_BORDER = GL13.GL_CLAMP_TO_BORDER;
	public static final int GL_CLAMP_TO_EDGE = GL12.GL_CLAMP_TO_EDGE;
	public static final int GL_CLAMP_VERTEX_COLOR = GL30.GL_CLAMP_VERTEX_COLOR;
	public static final int GL_CLEAR = GL11.GL_CLEAR;
	public static final int GL_CLEAR_BUFFER = GL43.GL_CLEAR_BUFFER;
	public static final int GL_CLEAR_TEXTURE = GL44.GL_CLEAR_TEXTURE;
	public static final int GL_CLIENT_ACTIVE_TEXTURE = GL13.GL_CLIENT_ACTIVE_TEXTURE;
	public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = GL11.GL_CLIENT_ATTRIB_STACK_DEPTH;
	public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = GL44.GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT;
	public static final int GL_CLIENT_PIXEL_STORE_BIT = GL11.GL_CLIENT_PIXEL_STORE_BIT;
	public static final int GL_CLIENT_STORAGE_BIT = GL44.GL_CLIENT_STORAGE_BIT;
	public static final int GL_CLIENT_VERTEX_ARRAY_BIT = GL11.GL_CLIENT_VERTEX_ARRAY_BIT;
	public static final int GL_CLIP_DISTANCE0 = GL30.GL_CLIP_DISTANCE0;
	public static final int GL_CLIP_DISTANCE1 = GL30.GL_CLIP_DISTANCE1;
	public static final int GL_CLIP_DISTANCE2 = GL30.GL_CLIP_DISTANCE2;
	public static final int GL_CLIP_DISTANCE3 = GL30.GL_CLIP_DISTANCE3;
	public static final int GL_CLIP_DISTANCE4 = GL30.GL_CLIP_DISTANCE4;
	public static final int GL_CLIP_DISTANCE5 = GL30.GL_CLIP_DISTANCE5;
	public static final int GL_CLIP_DISTANCE6 = GL30.GL_CLIP_DISTANCE6;
	public static final int GL_CLIP_DISTANCE7 = GL30.GL_CLIP_DISTANCE7;
	public static final int GL_CLIP_PLANE0 = GL11.GL_CLIP_PLANE0;
	public static final int GL_CLIP_PLANE1 = GL11.GL_CLIP_PLANE1;
	public static final int GL_CLIP_PLANE2 = GL11.GL_CLIP_PLANE2;
	public static final int GL_CLIP_PLANE3 = GL11.GL_CLIP_PLANE3;
	public static final int GL_CLIP_PLANE4 = GL11.GL_CLIP_PLANE4;
	public static final int GL_CLIP_PLANE5 = GL11.GL_CLIP_PLANE5;
	public static final int GL_COEFF = GL11.GL_COEFF;
	public static final int GL_COLOR = GL11.GL_COLOR;
	public static final int GL_COLOR_ARRAY = GL11.GL_COLOR_ARRAY;
	public static final int GL_COLOR_ARRAY_BUFFER_BINDING = GL15.GL_COLOR_ARRAY_BUFFER_BINDING;
	public static final int GL_COLOR_ARRAY_POINTER = GL11.GL_COLOR_ARRAY_POINTER;
	public static final int GL_COLOR_ARRAY_SIZE = GL11.GL_COLOR_ARRAY_SIZE;
	public static final int GL_COLOR_ARRAY_STRIDE = GL11.GL_COLOR_ARRAY_STRIDE;
	public static final int GL_COLOR_ARRAY_TYPE = GL11.GL_COLOR_ARRAY_TYPE;
	public static final int GL_COLOR_ATTACHMENT0 = GL30.GL_COLOR_ATTACHMENT0;
	public static final int GL_COLOR_ATTACHMENT1 = GL30.GL_COLOR_ATTACHMENT1;
	public static final int GL_COLOR_ATTACHMENT10 = GL30.GL_COLOR_ATTACHMENT10;
	public static final int GL_COLOR_ATTACHMENT11 = GL30.GL_COLOR_ATTACHMENT11;
	public static final int GL_COLOR_ATTACHMENT12 = GL30.GL_COLOR_ATTACHMENT12;
	public static final int GL_COLOR_ATTACHMENT13 = GL30.GL_COLOR_ATTACHMENT13;
	public static final int GL_COLOR_ATTACHMENT14 = GL30.GL_COLOR_ATTACHMENT14;
	public static final int GL_COLOR_ATTACHMENT15 = GL30.GL_COLOR_ATTACHMENT15;
	public static final int GL_COLOR_ATTACHMENT2 = GL30.GL_COLOR_ATTACHMENT2;
	public static final int GL_COLOR_ATTACHMENT3 = GL30.GL_COLOR_ATTACHMENT3;
	public static final int GL_COLOR_ATTACHMENT4 = GL30.GL_COLOR_ATTACHMENT4;
	public static final int GL_COLOR_ATTACHMENT5 = GL30.GL_COLOR_ATTACHMENT5;
	public static final int GL_COLOR_ATTACHMENT6 = GL30.GL_COLOR_ATTACHMENT6;
	public static final int GL_COLOR_ATTACHMENT7 = GL30.GL_COLOR_ATTACHMENT7;
	public static final int GL_COLOR_ATTACHMENT8 = GL30.GL_COLOR_ATTACHMENT8;
	public static final int GL_COLOR_ATTACHMENT9 = GL30.GL_COLOR_ATTACHMENT9;
	public static final int GL_COLOR_BUFFER_BIT = GL11.GL_COLOR_BUFFER_BIT;
	public static final int GL_COLOR_CLEAR_VALUE = GL11.GL_COLOR_CLEAR_VALUE;
	public static final int GL_COLOR_COMPONENTS = GL43.GL_COLOR_COMPONENTS;
	public static final int GL_COLOR_ENCODING = GL43.GL_COLOR_ENCODING;
	public static final int GL_COLOR_INDEX = GL11.GL_COLOR_INDEX;
	public static final int GL_COLOR_INDEXES = GL11.GL_COLOR_INDEXES;
	public static final int GL_COLOR_LOGIC_OP = GL11.GL_COLOR_LOGIC_OP;
	public static final int GL_COLOR_MATERIAL = GL11.GL_COLOR_MATERIAL;
	public static final int GL_COLOR_MATERIAL_FACE = GL11.GL_COLOR_MATERIAL_FACE;
	public static final int GL_COLOR_MATERIAL_PARAMETER = GL11.GL_COLOR_MATERIAL_PARAMETER;
	public static final int GL_COLOR_RENDERABLE = GL43.GL_COLOR_RENDERABLE;
	public static final int GL_COLOR_SUM = GL14.GL_COLOR_SUM;
	public static final int GL_COLOR_WRITEMASK = GL11.GL_COLOR_WRITEMASK;
	public static final int GL_COMBINE = GL13.GL_COMBINE;
	public static final int GL_COMBINE_ALPHA = GL13.GL_COMBINE_ALPHA;
	public static final int GL_COMBINE_RGB = GL13.GL_COMBINE_RGB;
	public static final int GL_COMMAND_BARRIER_BIT = GL42.GL_COMMAND_BARRIER_BIT;
	public static final int GL_COMPARE_R_TO_TEXTURE = GL14.GL_COMPARE_R_TO_TEXTURE;
	public static final int GL_COMPARE_REF_DEPTH_TO_TEXTURE = GL30.GL_COMPARE_REF_DEPTH_TO_TEXTURE;
	public static final int GL_COMPARE_REF_TO_TEXTURE = GL30.GL_COMPARE_REF_TO_TEXTURE;
	public static final int GL_COMPATIBLE_SUBROUTINES = GL40.GL_COMPATIBLE_SUBROUTINES;
	public static final int GL_COMPILE = GL11.GL_COMPILE;
	public static final int GL_COMPILE_AND_EXECUTE = GL11.GL_COMPILE_AND_EXECUTE;
	public static final int GL_COMPILE_STATUS = GL20.GL_COMPILE_STATUS;
	public static final int GL_COMPRESSED_ALPHA = GL13.GL_COMPRESSED_ALPHA;
	public static final int GL_COMPRESSED_INTENSITY = GL13.GL_COMPRESSED_INTENSITY;
	public static final int GL_COMPRESSED_LUMINANCE = GL13.GL_COMPRESSED_LUMINANCE;
	public static final int GL_COMPRESSED_LUMINANCE_ALPHA = GL13.GL_COMPRESSED_LUMINANCE_ALPHA;
	public static final int GL_COMPRESSED_R11_EAC = GL43.GL_COMPRESSED_R11_EAC;
	public static final int GL_COMPRESSED_RED = GL30.GL_COMPRESSED_RED;
	public static final int GL_COMPRESSED_RED_RGTC1 = GL30.GL_COMPRESSED_RED_RGTC1;
	public static final int GL_COMPRESSED_RG = GL30.GL_COMPRESSED_RG;
	public static final int GL_COMPRESSED_RG_RGTC2 = GL30.GL_COMPRESSED_RG_RGTC2;
	public static final int GL_COMPRESSED_RG11_EAC = GL43.GL_COMPRESSED_RG11_EAC;
	public static final int GL_COMPRESSED_RGB = GL13.GL_COMPRESSED_RGB;
	public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT = GL42.GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT;
	public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = GL42.GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT;
	public static final int GL_COMPRESSED_RGB8_ETC2 = GL43.GL_COMPRESSED_RGB8_ETC2;
	public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = GL43.GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2;
	public static final int GL_COMPRESSED_RGBA = GL13.GL_COMPRESSED_RGBA;
	public static final int GL_COMPRESSED_RGBA_BPTC_UNORM = GL42.GL_COMPRESSED_RGBA_BPTC_UNORM;
	public static final int GL_COMPRESSED_RGBA8_ETC2_EAC = GL43.GL_COMPRESSED_RGBA8_ETC2_EAC;
	public static final int GL_COMPRESSED_SIGNED_R11_EAC = GL43.GL_COMPRESSED_SIGNED_R11_EAC;
	public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = GL30.GL_COMPRESSED_SIGNED_RED_RGTC1;
	public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = GL30.GL_COMPRESSED_SIGNED_RG_RGTC2;
	public static final int GL_COMPRESSED_SIGNED_RG11_EAC = GL43.GL_COMPRESSED_SIGNED_RG11_EAC;
	public static final int GL_COMPRESSED_SLUMINANCE = GL21.GL_COMPRESSED_SLUMINANCE;
	public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = GL21.GL_COMPRESSED_SLUMINANCE_ALPHA;
	public static final int GL_COMPRESSED_SRGB = GL21.GL_COMPRESSED_SRGB;
	public static final int GL_COMPRESSED_SRGB_ALPHA = GL21.GL_COMPRESSED_SRGB_ALPHA;
	public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM = GL42.GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM;
	public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = GL43.GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC;
	public static final int GL_COMPRESSED_SRGB8_ETC2 = GL43.GL_COMPRESSED_SRGB8_ETC2;
	public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = GL43.GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2;
	public static final int GL_COMPRESSED_TEXTURE_FORMATS = GL13.GL_COMPRESSED_TEXTURE_FORMATS;
	public static final int GL_COMPUTE_SHADER = GL43.GL_COMPUTE_SHADER;
	public static final int GL_COMPUTE_SHADER_BIT = GL43.GL_COMPUTE_SHADER_BIT;
	public static final int GL_COMPUTE_SUBROUTINE = GL43.GL_COMPUTE_SUBROUTINE;
	public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = GL43.GL_COMPUTE_SUBROUTINE_UNIFORM;
	public static final int GL_COMPUTE_TEXTURE = GL43.GL_COMPUTE_TEXTURE;
	public static final int GL_COMPUTE_WORK_GROUP_SIZE = GL43.GL_COMPUTE_WORK_GROUP_SIZE;
	public static final int GL_CONDITION_SATISFIED = GL32.GL_CONDITION_SATISFIED;
	public static final int GL_CONSTANT = GL13.GL_CONSTANT;
	public static final int GL_CONSTANT_ALPHA = GL11.GL_CONSTANT_ALPHA;
	public static final int GL_CONSTANT_ATTENUATION = GL11.GL_CONSTANT_ATTENUATION;
	public static final int GL_CONSTANT_COLOR = GL11.GL_CONSTANT_COLOR;
	public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = GL32.GL_CONTEXT_COMPATIBILITY_PROFILE_BIT;
	public static final int GL_CONTEXT_CORE_PROFILE_BIT = GL32.GL_CONTEXT_CORE_PROFILE_BIT;
	public static final int GL_CONTEXT_FLAG_DEBUG_BIT = GL43.GL_CONTEXT_FLAG_DEBUG_BIT;
	public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = GL30.GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT;
	public static final int GL_CONTEXT_FLAGS = GL30.GL_CONTEXT_FLAGS;
	public static final int GL_CONTEXT_PROFILE_MASK = GL32.GL_CONTEXT_PROFILE_MASK;
	public static final int GL_COORD_REPLACE = GL20.GL_COORD_REPLACE;
	public static final int GL_COPY = GL11.GL_COPY;
	public static final int GL_COPY_INVERTED = GL11.GL_COPY_INVERTED;
	public static final int GL_COPY_PIXEL_TOKEN = GL11.GL_COPY_PIXEL_TOKEN;
	public static final int GL_COPY_READ_BUFFER = GL31.GL_COPY_READ_BUFFER;
	public static final int GL_COPY_READ_BUFFER_BINDING = GL31.GL_COPY_READ_BUFFER_BINDING;
	public static final int GL_COPY_WRITE_BUFFER = GL31.GL_COPY_WRITE_BUFFER;
	public static final int GL_COPY_WRITE_BUFFER_BINDING = GL31.GL_COPY_WRITE_BUFFER_BINDING;
	public static final int GL_CULL_FACE = GL11.GL_CULL_FACE;
	public static final int GL_CULL_FACE_MODE = GL11.GL_CULL_FACE_MODE;
	public static final int GL_CURRENT_BIT = GL11.GL_CURRENT_BIT;
	public static final int GL_CURRENT_COLOR = GL11.GL_CURRENT_COLOR;
	public static final int GL_CURRENT_FOG_COORD = GL15.GL_CURRENT_FOG_COORD;
	public static final int GL_CURRENT_FOG_COORDINATE = GL14.GL_CURRENT_FOG_COORDINATE;
	public static final int GL_CURRENT_INDEX = GL11.GL_CURRENT_INDEX;
	public static final int GL_CURRENT_NORMAL = GL11.GL_CURRENT_NORMAL;
	public static final int GL_CURRENT_PROGRAM = GL20.GL_CURRENT_PROGRAM;
	public static final int GL_CURRENT_QUERY = GL15.GL_CURRENT_QUERY;
	public static final int GL_CURRENT_RASTER_COLOR = GL11.GL_CURRENT_RASTER_COLOR;
	public static final int GL_CURRENT_RASTER_DISTANCE = GL11.GL_CURRENT_RASTER_DISTANCE;
	public static final int GL_CURRENT_RASTER_INDEX = GL11.GL_CURRENT_RASTER_INDEX;
	public static final int GL_CURRENT_RASTER_POSITION = GL11.GL_CURRENT_RASTER_POSITION;
	public static final int GL_CURRENT_RASTER_POSITION_VALID = GL11.GL_CURRENT_RASTER_POSITION_VALID;
	public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = GL21.GL_CURRENT_RASTER_SECONDARY_COLOR;
	public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = GL11.GL_CURRENT_RASTER_TEXTURE_COORDS;
	public static final int GL_CURRENT_SECONDARY_COLOR = GL14.GL_CURRENT_SECONDARY_COLOR;
	public static final int GL_CURRENT_TEXTURE_COORDS = GL11.GL_CURRENT_TEXTURE_COORDS;
	public static final int GL_CURRENT_VERTEX_ATTRIB = GL20.GL_CURRENT_VERTEX_ATTRIB;
	public static final int GL_CW = GL11.GL_CW;
	
	//XXX D
	
	public static final int GL_DEBUG_CALLBACK_FUNCTION = GL43.GL_DEBUG_CALLBACK_FUNCTION;
	public static final int GL_DEBUG_CALLBACK_USER_PARAM = GL43.GL_DEBUG_CALLBACK_USER_PARAM;
	public static final int GL_DEBUG_GROUP_STACK_DEPTH = GL43.GL_DEBUG_GROUP_STACK_DEPTH;
	public static final int GL_DEBUG_LOGGED_MESSAGES = GL43.GL_DEBUG_LOGGED_MESSAGES;
	public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = GL43.GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH;
	public static final int GL_DEBUG_OUTPUT = GL43.GL_DEBUG_OUTPUT;
	public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS;
	public static final int GL_DEBUG_SEVERITY_HIGH = GL43.GL_DEBUG_SEVERITY_HIGH;
	public static final int GL_DEBUG_SEVERITY_LOW = GL43.GL_DEBUG_SEVERITY_LOW;
	public static final int GL_DEBUG_SEVERITY_MEDIUM = GL43.GL_DEBUG_SEVERITY_MEDIUM;
	public static final int GL_DEBUG_SEVERITY_NOTIFICATION = GL43.GL_DEBUG_SEVERITY_NOTIFICATION;
	public static final int GL_DEBUG_SOURCE_API = GL43.GL_DEBUG_SOURCE_API;
	public static final int GL_DEBUG_SOURCE_APPLICATION = GL43.GL_DEBUG_SOURCE_APPLICATION;
	public static final int GL_DEBUG_SOURCE_OTHER = GL43.GL_DEBUG_SOURCE_OTHER;
	public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = GL43.GL_DEBUG_SOURCE_SHADER_COMPILER;
	public static final int GL_DEBUG_SOURCE_THIRD_PARTY = GL43.GL_DEBUG_SOURCE_THIRD_PARTY;
	public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM;
	public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR;
	public static final int GL_DEBUG_TYPE_ERROR = GL43.GL_DEBUG_TYPE_ERROR;
	public static final int GL_DEBUG_TYPE_MARKER = GL43.GL_DEBUG_TYPE_MARKER;
	public static final int GL_DEBUG_TYPE_OTHER = GL43.GL_DEBUG_TYPE_OTHER;
	public static final int GL_DEBUG_TYPE_PERFORMANCE = GL43.GL_DEBUG_TYPE_PERFORMANCE;
	public static final int GL_DEBUG_TYPE_POP_GROUP = GL43.GL_DEBUG_TYPE_POP_GROUP;
	public static final int GL_DEBUG_TYPE_PORTABILITY = GL43.GL_DEBUG_TYPE_PORTABILITY;
	public static final int GL_DEBUG_TYPE_PUSH_GROUP = GL43.GL_DEBUG_TYPE_PUSH_GROUP;
	public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR;
	public static final int GL_DECAL = GL11.GL_DECAL;
	public static final int GL_DECR = GL11.GL_DECR;
	public static final int GL_DECR_WRAP = GL14.GL_DECR_WRAP;
	public static final int GL_DELETE_STATUS = GL20.GL_DELETE_STATUS;
	public static final int GL_DEPTH = GL11.GL_DEPTH;
	public static final int GL_DEPTH_ATTACHMENT = GL30.GL_DEPTH_ATTACHMENT;
	public static final int GL_DEPTH_BIAS = GL11.GL_DEPTH_BIAS;
	public static final int GL_DEPTH_BITS = GL11.GL_DEPTH_BITS;
	public static final int GL_DEPTH_BUFFER = GL30.GL_DEPTH_BUFFER;
	public static final int GL_DEPTH_BUFFER_BIT = GL11.GL_DEPTH_BUFFER_BIT;
	public static final int GL_DEPTH_CLAMP = GL32.GL_DEPTH_CLAMP;
	public static final int GL_DEPTH_CLEAR_VALUE = GL11.GL_DEPTH_CLEAR_VALUE;
	public static final int GL_DEPTH_COMPONENT = GL11.GL_DEPTH_COMPONENT;
	public static final int GL_DEPTH_COMPONENT16 = GL14.GL_DEPTH_COMPONENT16;
	public static final int GL_DEPTH_COMPONENT24 = GL14.GL_DEPTH_COMPONENT24;
	public static final int GL_DEPTH_COMPONENT32 = GL14.GL_DEPTH_COMPONENT32;
	public static final int GL_DEPTH_COMPONENT32F = GL30.GL_DEPTH_COMPONENT32F;
	public static final int GL_DEPTH_COMPONENTS = GL43.GL_DEPTH_COMPONENTS;
	public static final int GL_DEPTH_FUNC = GL11.GL_DEPTH_FUNC;
	public static final int GL_DEPTH_RANGE = GL41.GL_DEPTH_RANGE;
	public static final int GL_DEPTH_RENDERABLE = GL43.GL_DEPTH_RENDERABLE;
	public static final int GL_DEPTH_SCALE = GL11.GL_DEPTH_SCALE;
	public static final int GL_DEPTH_STENCIL = GL30.GL_DEPTH_STENCIL;
	public static final int GL_DEPTH_STENCIL_ATTACHMENT = GL30.GL_DEPTH_STENCIL_ATTACHMENT;
	public static final int GL_DEPTH_STENCIL_TEXTURE_MODE = GL43.GL_DEPTH_STENCIL_TEXTURE_MODE;
	public static final int GL_DEPTH_TEST = GL11.GL_DEPTH_TEST;
	public static final int GL_DEPTH_TEXTURE_MODE = GL14.GL_DEPTH_TEXTURE_MODE;
	public static final int GL_DEPTH_WRITEMASK = GL11.GL_DEPTH_WRITEMASK;
	public static final int GL_DEPTH24_STENCIL8 = GL30.GL_DEPTH24_STENCIL8;
	public static final int GL_DEPTH32F_STENCIL8 = GL30.GL_DEPTH32F_STENCIL8;
	public static final int GL_DIFFUSE = GL11.GL_DIFFUSE;
	public static final int GL_DISPATCH_INDIRECT_BUFFER = GL43.GL_DISPATCH_INDIRECT_BUFFER;
	public static final int GL_DISPATCH_INDIRECT_BUFFER_BINDING = GL43.GL_DISPATCH_INDIRECT_BUFFER_BINDING;
	public static final int GL_DISPLAY_LIST = GL43.GL_DISPLAY_LIST;
	public static final int GL_DITHER = GL11.GL_DITHER;
	public static final int GL_DOMAIN = GL11.GL_DOMAIN;
	public static final int GL_DONT_CARE = GL11.GL_DONT_CARE;
	public static final int GL_DOT3_RGB = GL13.GL_DOT3_RGB;
	public static final int GL_DOT3_RGBA = GL13.GL_DOT3_RGBA;
	public static final int GL_DOUBLE = GL11.GL_DOUBLE;
	public static final int GL_DOUBLE_MAT2 = GL41.GL_DOUBLE_MAT2;
	public static final int GL_DOUBLE_MAT2x3 = GL41.GL_DOUBLE_MAT2x3;
	public static final int GL_DOUBLE_MAT2x4 = GL41.GL_DOUBLE_MAT2x4;
	public static final int GL_DOUBLE_MAT3 = GL41.GL_DOUBLE_MAT3;
	public static final int GL_DOUBLE_MAT3x2 = GL41.GL_DOUBLE_MAT3x2;
	public static final int GL_DOUBLE_MAT3x4 = GL41.GL_DOUBLE_MAT3x4;
	public static final int GL_DOUBLE_MAT4 = GL41.GL_DOUBLE_MAT4;
	public static final int GL_DOUBLE_MAT4x2 = GL41.GL_DOUBLE_MAT4x2;
	public static final int GL_DOUBLE_MAT4x3 = GL41.GL_DOUBLE_MAT4x3;
	public static final int GL_DOUBLE_VEC2 = GL41.GL_DOUBLE_VEC2;
	public static final int GL_DOUBLE_VEC3 = GL41.GL_DOUBLE_VEC3;
	public static final int GL_DOUBLE_VEC4 = GL41.GL_DOUBLE_VEC4;
	public static final int GL_DOUBLEBUFFER = GL11.GL_DOUBLEBUFFER;
	public static final int GL_DRAW_BUFFER = GL11.GL_DRAW_BUFFER;
	public static final int GL_DRAW_BUFFER0 = GL20.GL_DRAW_BUFFER0;
	public static final int GL_DRAW_BUFFER1 = GL20.GL_DRAW_BUFFER1;
	public static final int GL_DRAW_BUFFER10 = GL20.GL_DRAW_BUFFER10;
	public static final int GL_DRAW_BUFFER11 = GL20.GL_DRAW_BUFFER11;
	public static final int GL_DRAW_BUFFER12 = GL20.GL_DRAW_BUFFER12;
	public static final int GL_DRAW_BUFFER13 = GL20.GL_DRAW_BUFFER13;
	public static final int GL_DRAW_BUFFER14 = GL20.GL_DRAW_BUFFER14;
	public static final int GL_DRAW_BUFFER15 = GL20.GL_DRAW_BUFFER15;
	public static final int GL_DRAW_BUFFER2 = GL20.GL_DRAW_BUFFER2;
	public static final int GL_DRAW_BUFFER3 = GL20.GL_DRAW_BUFFER3;
	public static final int GL_DRAW_BUFFER4 = GL20.GL_DRAW_BUFFER4;
	public static final int GL_DRAW_BUFFER5 = GL20.GL_DRAW_BUFFER5;
	public static final int GL_DRAW_BUFFER6 = GL20.GL_DRAW_BUFFER6;
	public static final int GL_DRAW_BUFFER7 = GL20.GL_DRAW_BUFFER7;
	public static final int GL_DRAW_BUFFER8 = GL20.GL_DRAW_BUFFER8;
	public static final int GL_DRAW_BUFFER9 = GL20.GL_DRAW_BUFFER9;
	public static final int GL_DRAW_FRAMEBUFFER = GL30.GL_DRAW_FRAMEBUFFER;
	public static final int GL_DRAW_FRAMEBUFFER_BINDING = GL30.GL_DRAW_FRAMEBUFFER_BINDING;
	public static final int GL_DRAW_INDIRECT_BUFFER = GL40.GL_DRAW_INDIRECT_BUFFER;
	public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = GL40.GL_DRAW_INDIRECT_BUFFER_BINDING;
	public static final int GL_DRAW_PIXEL_TOKEN = GL11.GL_DRAW_PIXEL_TOKEN;
	public static final int GL_DST_ALPHA = GL11.GL_DST_ALPHA;
	public static final int GL_DST_COLOR = GL11.GL_DST_COLOR;
	public static final int GL_DYNAMIC_COPY = GL15.GL_DYNAMIC_COPY;
	public static final int GL_DYNAMIC_DRAW = GL15.GL_DYNAMIC_DRAW;
	public static final int GL_DYNAMIC_READ = GL15.GL_DYNAMIC_READ;
	public static final int GL_DYNAMIC_STORAGE_BIT = GL44.GL_DYNAMIC_STORAGE_BIT;
	
	//XXX E
	
	public static final int GL_EDGE_FLAG = GL11.GL_EDGE_FLAG;
	public static final int GL_EDGE_FLAG_ARRAY = GL11.GL_EDGE_FLAG_ARRAY;
	public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = GL15.GL_EDGE_FLAG_ARRAY_BUFFER_BINDING;
	public static final int GL_EDGE_FLAG_ARRAY_POINTER = GL11.GL_EDGE_FLAG_ARRAY_POINTER;
	public static final int GL_EDGE_FLAG_ARRAY_STRIDE = GL11.GL_EDGE_FLAG_ARRAY_STRIDE;
	public static final int GL_ELEMENT_ARRAY_BARRIER_BIT = GL42.GL_ELEMENT_ARRAY_BARRIER_BIT;
	public static final int GL_ELEMENT_ARRAY_BUFFER = GL15.GL_ELEMENT_ARRAY_BUFFER;
	public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING;
	public static final int GL_EMISSION = GL11.GL_EMISSION;
	public static final int GL_ENABLE_BIT = GL11.GL_ENABLE_BIT;
	public static final int GL_EQUAL = GL11.GL_EQUAL;
	public static final int GL_EQUIV = GL11.GL_EQUIV;
	public static final int GL_EVAL_BIT = GL11.GL_EVAL_BIT;
	public static final int GL_EXP = GL11.GL_EXP;
	public static final int GL_EXP2 = GL11.GL_EXP2;
	public static final int GL_EXTENSIONS = GL11.GL_EXTENSIONS;
	public static final int GL_EYE_LINEAR = GL11.GL_EYE_LINEAR;
	public static final int GL_EYE_PLANE = GL11.GL_EYE_PLANE;
	
	//XXX F
	
	public static final int GL_FALSE = GL11.GL_FALSE;
	public static final int GL_FASTEST = GL11.GL_FASTEST;
	public static final int GL_FEEDBACK = GL11.GL_FEEDBACK;
	public static final int GL_FEEDBACK_BUFFER_POINTER = GL11.GL_FEEDBACK_BUFFER_POINTER;
	public static final int GL_FEEDBACK_BUFFER_SIZE = GL11.GL_FEEDBACK_BUFFER_SIZE;
	public static final int GL_FEEDBACK_BUFFER_TYPE = GL11.GL_FEEDBACK_BUFFER_TYPE;
	public static final int GL_FILL = GL11.GL_FILL;
	public static final int GL_FILTER = GL43.GL_FILTER;
	public static final int GL_FIRST_VERTEX_CONVENTION = GL41.GL_FIRST_VERTEX_CONVENTION;
	public static final int GL_FIXED = GL41.GL_FIXED;
	public static final int GL_FIXED_ONLY = GL30.GL_FIXED_ONLY;
	public static final int GL_FLAT = GL11.GL_FLAT;
	public static final int GL_FLOAT = GL11.GL_FLOAT;
	public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV;
	public static final int GL_FLOAT_MAT2 = GL20.GL_FLOAT_MAT2;
	public static final int GL_FLOAT_MAT2x3 = GL21.GL_FLOAT_MAT2x3;
	public static final int GL_FLOAT_MAT2x4 = GL21.GL_FLOAT_MAT2x4;
	public static final int GL_FLOAT_MAT3 = GL20.GL_FLOAT_MAT3;
	public static final int GL_FLOAT_MAT3x2 = GL21.GL_FLOAT_MAT3x2;
	public static final int GL_FLOAT_MAT3x4 = GL21.GL_FLOAT_MAT3x4;
	public static final int GL_FLOAT_MAT4 = GL20.GL_FLOAT_MAT4;
	public static final int GL_FLOAT_MAT4x2 = GL21.GL_FLOAT_MAT4x2;
	public static final int GL_FLOAT_MAT4x3 = GL21.GL_FLOAT_MAT4x3;
	public static final int GL_FLOAT_VEC2 = GL20.GL_FLOAT_VEC2;
	public static final int GL_FLOAT_VEC3 = GL20.GL_FLOAT_VEC3;
	public static final int GL_FLOAT_VEC4 = GL20.GL_FLOAT_VEC4;
	public static final int GL_FOG = GL11.GL_FOG;
	public static final int GL_FOG_BIT = GL11.GL_FOG_BIT;
	public static final int GL_FOG_COLOR = GL11.GL_FOG_COLOR;
	public static final int GL_FOG_COORD = GL15.GL_FOG_COORD;
	public static final int GL_FOG_COORD_ARRAY = GL15.GL_FOG_COORD_ARRAY;
	public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = GL15.GL_FOG_COORD_ARRAY_BUFFER_BINDING;
	public static final int GL_FOG_COORD_ARRAY_POINTER = GL15.GL_FOG_COORD_ARRAY_POINTER;
	public static final int GL_FOG_COORD_ARRAY_STRIDE = GL15.GL_FOG_COORD_ARRAY_STRIDE;
	public static final int GL_FOG_COORD_ARRAY_TYPE = GL15.GL_FOG_COORD_ARRAY_TYPE;
	public static final int GL_FOG_COORD_SRC = GL15.GL_FOG_COORD_SRC;
	public static final int GL_FOG_COORDINATE = GL14.GL_FOG_COORDINATE;
	public static final int GL_FOG_COORDINATE_ARRAY = GL14.GL_FOG_COORDINATE_ARRAY;
	public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = GL15.GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING;
	public static final int GL_FOG_COORDINATE_ARRAY_POINTER = GL14.GL_FOG_COORDINATE_ARRAY_POINTER;
	public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = GL14.GL_FOG_COORDINATE_ARRAY_STRIDE;
	public static final int GL_FOG_COORDINATE_ARRAY_TYPE = GL14.GL_FOG_COORDINATE_ARRAY_TYPE;
	public static final int GL_FOG_COORDINATE_SOURCE = GL14.GL_FOG_COORDINATE_SOURCE;
	public static final int GL_FOG_DENSITY = GL11.GL_FOG_DENSITY;
	public static final int GL_FOG_END = GL11.GL_FOG_END;
	public static final int GL_FOG_HINT = GL11.GL_FOG_HINT;
	public static final int GL_FOG_INDEX = GL11.GL_FOG_INDEX;
	public static final int GL_FOG_MODE = GL11.GL_FOG_MODE;
	public static final int GL_FOG_START = GL11.GL_FOG_START;
	public static final int GL_FRACTIONAL_EVEN = GL40.GL_FRACTIONAL_EVEN;
	public static final int GL_FRACTIONAL_ODD = GL40.GL_FRACTIONAL_ODD;
	public static final int GL_FRAGMENT_DEPTH = GL14.GL_FRAGMENT_DEPTH;
	public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = GL40.GL_FRAGMENT_INTERPOLATION_OFFSET_BITS;
	public static final int GL_FRAGMENT_SHADER = GL20.GL_FRAGMENT_SHADER;
	public static final int GL_FRAGMENT_SHADER_BIT = GL41.GL_FRAGMENT_SHADER_BIT;
	public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = GL20.GL_FRAGMENT_SHADER_DERIVATIVE_HINT;
	public static final int GL_FRAGMENT_SUBROUTINE = GL43.GL_FRAGMENT_SUBROUTINE;
	public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = GL43.GL_FRAGMENT_SUBROUTINE_UNIFORM;
	public static final int GL_FRAGMENT_TEXTURE = GL43.GL_FRAGMENT_TEXTURE;
	public static final int GL_FRAMEBUFFER = GL30.GL_FRAMEBUFFER;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = GL30.GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = GL30.GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = GL32.GL_FRAMEBUFFER_ATTACHMENT_LAYERED;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = GL30.GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = GL30.GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = GL30.GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = GL30.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = GL32.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = GL30.GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL;
	public static final int GL_FRAMEBUFFER_BARRIER_BIT = GL42.GL_FRAMEBUFFER_BARRIER_BIT;
	public static final int GL_FRAMEBUFFER_BINDING = GL30.GL_FRAMEBUFFER_BINDING;
	public static final int GL_FRAMEBUFFER_BLEND = GL43.GL_FRAMEBUFFER_BLEND;
	public static final int GL_FRAMEBUFFER_COMPLETE = GL30.GL_FRAMEBUFFER_COMPLETE;
	public static final int GL_FRAMEBUFFER_DEFAULT = GL30.GL_FRAMEBUFFER_DEFAULT;
	public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = GL43.GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS;
	public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = GL43.GL_FRAMEBUFFER_DEFAULT_HEIGHT;
	public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = GL43.GL_FRAMEBUFFER_DEFAULT_LAYERS;
	public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = GL43.GL_FRAMEBUFFER_DEFAULT_SAMPLES;
	public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = GL43.GL_FRAMEBUFFER_DEFAULT_WIDTH;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
	public static final int GL_FRAMEBUFFER_RENDERABLE = GL43.GL_FRAMEBUFFER_RENDERABLE;
	public static final int GL_FRAMEBUFFER_RENDERABLE_LAYERED = GL43.GL_FRAMEBUFFER_RENDERABLE_LAYERED;
	public static final int GL_FRAMEBUFFER_SRGB = GL30.GL_FRAMEBUFFER_SRGB;
	public static final int GL_FRAMEBUFFER_SRGB_CAPABLE = GL30.GL_FRAMEBUFFER_SRGB_CAPABLE;
	public static final int GL_FRAMEBUFFER_UNDEFINED = GL30.GL_FRAMEBUFFER_UNDEFINED;
	public static final int GL_FRAMEBUFFER_UNSUPPORTED = GL30.GL_FRAMEBUFFER_UNSUPPORTED;
	public static final int GL_FRONT = GL11.GL_FRONT;
	public static final int GL_FRONT_AND_BACK = GL11.GL_FRONT_AND_BACK;
	public static final int GL_FRONT_FACE = GL11.GL_FRONT_FACE;
	public static final int GL_FRONT_LEFT = GL11.GL_FRONT_LEFT;
	public static final int GL_FRONT_RIGHT = GL11.GL_FRONT_RIGHT;
	public static final int GL_FULL_SUPPORT = GL43.GL_FULL_SUPPORT;
	public static final int GL_FUNC_ADD = GL14.GL_FUNC_ADD;
	public static final int GL_FUNC_REVERSE_SUBTRACT = GL14.GL_FUNC_REVERSE_SUBTRACT;
	public static final int GL_FUNC_SUBTRACT = GL14.GL_FUNC_SUBTRACT;
	
	//XXX G
	
	public static final int GL_GENERATE_MIPMAP = GL14.GL_GENERATE_MIPMAP;
	public static final int GL_GENERATE_MIPMAP_HINT = GL14.GL_GENERATE_MIPMAP_HINT;
	public static final int GL_GEOMETRY_INPUT_TYPE = GL32.GL_GEOMETRY_INPUT_TYPE;
	public static final int GL_GEOMETRY_OUTPUT_TYPE = GL32.GL_GEOMETRY_OUTPUT_TYPE;
	public static final int GL_GEOMETRY_SHADER = GL32.GL_GEOMETRY_SHADER;
	public static final int GL_GEOMETRY_SHADER_BIT = GL41.GL_GEOMETRY_SHADER_BIT;
	public static final int GL_GEOMETRY_SHADER_INVOCATIONS = GL40.GL_GEOMETRY_SHADER_INVOCATIONS;
	public static final int GL_GEOMETRY_SUBROUTINE = GL43.GL_GEOMETRY_SUBROUTINE;
	public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = GL43.GL_GEOMETRY_SUBROUTINE_UNIFORM;
	public static final int GL_GEOMETRY_TEXTURE = GL43.GL_GEOMETRY_TEXTURE;
	public static final int GL_GEOMETRY_VERTICES_OUT = GL32.GL_GEOMETRY_VERTICES_OUT;
	public static final int GL_GEQUAL = GL11.GL_GEQUAL;
	public static final int GL_GET_TEXTURE_IMAGE_FORMAT = GL43.GL_GET_TEXTURE_IMAGE_FORMAT;
	public static final int GL_GET_TEXTURE_IMAGE_TYPE = GL43.GL_GET_TEXTURE_IMAGE_TYPE;
	public static final int GL_GREATER = GL11.GL_GREATER;
	public static final int GL_GREEN = GL11.GL_GREEN;
	public static final int GL_GREEN_BIAS = GL11.GL_GREEN_BIAS;
	public static final int GL_GREEN_BITS = GL11.GL_GREEN_BITS;
	public static final int GL_GREEN_INTEGER = GL30.GL_GREEN_INTEGER;
	public static final int GL_GREEN_SCALE = GL11.GL_GREEN_SCALE;
	
	//XXX H
	
	public static final int GL_HALF_FLOAT = GL30.GL_HALF_FLOAT;
	public static final int GL_HIGH_FLOAT = GL41.GL_HIGH_FLOAT;
	public static final int GL_HIGH_INT = GL41.GL_HIGH_INT;
	public static final int GL_HINT_BIT = GL11.GL_HINT_BIT;
	
	//XXX I
	
	public static final int GL_IMAGE_1D = GL42.GL_IMAGE_1D;
	public static final int GL_IMAGE_1D_ARRAY = GL42.GL_IMAGE_1D_ARRAY;
	public static final int GL_IMAGE_2D = GL42.GL_IMAGE_2D;
	public static final int GL_IMAGE_2D_ARRAY = GL42.GL_IMAGE_2D_ARRAY;
	public static final int GL_IMAGE_2D_MULTISAMPLE = GL42.GL_IMAGE_2D_MULTISAMPLE;
	public static final int GL_IMAGE_2D_MULTISAMPLE_ARRAY = GL42.GL_IMAGE_2D_MULTISAMPLE_ARRAY;
	public static final int GL_IMAGE_2D_RECT = GL42.GL_IMAGE_2D_RECT;
	public static final int GL_IMAGE_3D = GL42.GL_IMAGE_3D;
	public static final int GL_IMAGE_BINDING_ACCESS = GL42.GL_IMAGE_BINDING_ACCESS;
	public static final int GL_IMAGE_BINDING_FORMAT = GL42.GL_IMAGE_BINDING_FORMAT;
	public static final int GL_IMAGE_BINDING_LAYER = GL42.GL_IMAGE_BINDING_LAYER;
	public static final int GL_IMAGE_BINDING_LAYERED = GL42.GL_IMAGE_BINDING_LAYERED;
	public static final int GL_IMAGE_BINDING_LEVEL = GL42.GL_IMAGE_BINDING_LEVEL;
	public static final int GL_IMAGE_BINDING_NAME = GL42.GL_IMAGE_BINDING_NAME;
	public static final int GL_IMAGE_BUFFER = GL42.GL_IMAGE_BUFFER;
	public static final int GL_IMAGE_CLASS_1_X_16 = GL43.GL_IMAGE_CLASS_1_X_16;
	public static final int GL_IMAGE_CLASS_1_X_32 = GL43.GL_IMAGE_CLASS_1_X_32;
	public static final int GL_IMAGE_CLASS_1_X_8 = GL43.GL_IMAGE_CLASS_1_X_8;
	public static final int GL_IMAGE_CLASS_10_10_10_2 = GL43.GL_IMAGE_CLASS_10_10_10_2;
	public static final int GL_IMAGE_CLASS_11_11_10 = GL43.GL_IMAGE_CLASS_11_11_10;
	public static final int GL_IMAGE_CLASS_2_X_16 = GL43.GL_IMAGE_CLASS_2_X_16;
	public static final int GL_IMAGE_CLASS_2_X_32 = GL43.GL_IMAGE_CLASS_2_X_32;
	public static final int GL_IMAGE_CLASS_2_X_8 = GL43.GL_IMAGE_CLASS_2_X_8;
	public static final int GL_IMAGE_CLASS_4_X_16 = GL43.GL_IMAGE_CLASS_4_X_16;
	public static final int GL_IMAGE_CLASS_4_X_32 = GL43.GL_IMAGE_CLASS_4_X_32;
	public static final int GL_IMAGE_CLASS_4_X_8 = GL43.GL_IMAGE_CLASS_4_X_8;
	public static final int GL_IMAGE_COMPATIBILITY_CLASS = GL43.GL_IMAGE_COMPATIBILITY_CLASS;
	public static final int GL_IMAGE_CUBE = GL42.GL_IMAGE_CUBE;
	public static final int GL_IMAGE_CUBE_MAP_ARRAY = GL42.GL_IMAGE_CUBE_MAP_ARRAY;
	public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = GL42.GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE;
	public static final int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = GL43.GL_IMAGE_FORMAT_COMPATIBILITY_TYPE;
	public static final int GL_IMAGE_PIXEL_FORMAT = GL43.GL_IMAGE_PIXEL_FORMAT;
	public static final int GL_IMAGE_PIXEL_TYPE = GL43.GL_IMAGE_PIXEL_TYPE;
	public static final int GL_IMAGE_TEXEL_SIZE = GL43.GL_IMAGE_TEXEL_SIZE;
	public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = GL41.GL_IMPLEMENTATION_COLOR_READ_FORMAT;
	public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = GL41.GL_IMPLEMENTATION_COLOR_READ_TYPE;
	public static final int GL_INCR = GL11.GL_INCR;
	public static final int GL_INCR_WRAP = GL14.GL_INCR_WRAP;
	public static final int GL_INDEX = GL30.GL_INDEX;
	public static final int GL_INDEX_ARRAY = GL11.GL_INDEX_ARRAY;
	public static final int GL_INDEX_ARRAY_BUFFER_BINDING = GL15.GL_INDEX_ARRAY_BUFFER_BINDING;
	public static final int GL_INDEX_ARRAY_POINTER = GL11.GL_INDEX_ARRAY_POINTER;
	public static final int GL_INDEX_ARRAY_STRIDE = GL11.GL_INDEX_ARRAY_STRIDE;
	public static final int GL_INDEX_ARRAY_TYPE = GL11.GL_INDEX_ARRAY_TYPE;
	public static final int GL_INDEX_BITS = GL11.GL_INDEX_BITS;
	public static final int GL_INDEX_CLEAR_VALUE = GL11.GL_INDEX_CLEAR_VALUE;
	public static final int GL_INDEX_LOGIC_OP = GL11.GL_INDEX_LOGIC_OP;
	public static final int GL_INDEX_MODE = GL11.GL_INDEX_MODE;
	public static final int GL_INDEX_OFFSET = GL11.GL_INDEX_OFFSET;
	public static final int GL_INDEX_SHIFT = GL11.GL_INDEX_SHIFT;
	public static final int GL_INDEX_WRITEMASK = GL11.GL_INDEX_WRITEMASK;
	public static final int GL_INFO_LOG_LENGTH = GL20.GL_INFO_LOG_LENGTH;
	public static final int GL_INT = GL11.GL_INT;
	public static final int GL_INT_2_10_10_10_REV = GL33.GL_INT_2_10_10_10_REV;
	public static final int GL_INT_IMAGE_1D = GL42.GL_INT_IMAGE_1D;
	public static final int GL_INT_IMAGE_1D_ARRAY = GL42.GL_INT_IMAGE_1D_ARRAY;
	public static final int GL_INT_IMAGE_2D = GL42.GL_INT_IMAGE_2D;
	public static final int GL_INT_IMAGE_2D_ARRAY = GL42.GL_INT_IMAGE_2D_ARRAY;
	public static final int GL_INT_IMAGE_2D_MULTISAMPLE = GL42.GL_INT_IMAGE_2D_MULTISAMPLE;
	public static final int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY = GL42.GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY;
	public static final int GL_INT_IMAGE_2D_RECT = GL42.GL_INT_IMAGE_2D_RECT;
	public static final int GL_INT_IMAGE_3D = GL42.GL_INT_IMAGE_3D;
	public static final int GL_INT_IMAGE_BUFFER = GL42.GL_INT_IMAGE_BUFFER;
	public static final int GL_INT_IMAGE_CUBE = GL42.GL_INT_IMAGE_CUBE;
	public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY = GL42.GL_INT_IMAGE_CUBE_MAP_ARRAY;
	public static final int GL_INT_SAMPLER_1D = GL30.GL_INT_SAMPLER_1D;
	public static final int GL_INT_SAMPLER_1D_ARRAY = GL30.GL_INT_SAMPLER_1D_ARRAY;
	public static final int GL_INT_SAMPLER_2D = GL30.GL_INT_SAMPLER_2D;
	public static final int GL_INT_SAMPLER_2D_ARRAY = GL30.GL_INT_SAMPLER_2D_ARRAY;
	public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = GL32.GL_INT_SAMPLER_2D_MULTISAMPLE;
	public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = GL32.GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY;
	public static final int GL_INT_SAMPLER_2D_RECT = GL30.GL_INT_SAMPLER_2D_RECT;
	public static final int GL_INT_SAMPLER_3D = GL30.GL_INT_SAMPLER_3D;
	public static final int GL_INT_SAMPLER_BUFFER = GL30.GL_INT_SAMPLER_BUFFER;
	public static final int GL_INT_SAMPLER_CUBE = GL30.GL_INT_SAMPLER_CUBE;
	public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = GL40.GL_INT_SAMPLER_CUBE_MAP_ARRAY;
	public static final int GL_INT_VEC2 = GL20.GL_INT_VEC2;
	public static final int GL_INT_VEC3 = GL20.GL_INT_VEC3;
	public static final int GL_INT_VEC4 = GL20.GL_INT_VEC4;
	public static final int GL_INTENSITY = GL11.GL_INTENSITY;
	public static final int GL_INTENSITY12 = GL11.GL_INTENSITY12;
	public static final int GL_INTENSITY16 = GL11.GL_INTENSITY16;
	public static final int GL_INTENSITY4 = GL11.GL_INTENSITY4;
	public static final int GL_INTENSITY8 = GL11.GL_INTENSITY8;
	public static final int GL_INTERLEAVED_ATTRIBS = GL30.GL_INTERLEAVED_ATTRIBS;
	public static final int GL_INTERNALFORMAT_ALPHA_SIZE = GL43.GL_INTERNALFORMAT_ALPHA_SIZE;
	public static final int GL_INTERNALFORMAT_ALPHA_TYPE = GL43.GL_INTERNALFORMAT_ALPHA_TYPE;
	public static final int GL_INTERNALFORMAT_BLUE_SIZE = GL43.GL_INTERNALFORMAT_BLUE_SIZE;
	public static final int GL_INTERNALFORMAT_BLUE_TYPE = GL43.GL_INTERNALFORMAT_BLUE_TYPE;
	public static final int GL_INTERNALFORMAT_DEPTH_SIZE = GL43.GL_INTERNALFORMAT_DEPTH_SIZE;
	public static final int GL_INTERNALFORMAT_DEPTH_TYPE = GL43.GL_INTERNALFORMAT_DEPTH_TYPE;
	public static final int GL_INTERNALFORMAT_GREEN_SIZE = GL43.GL_INTERNALFORMAT_GREEN_SIZE;
	public static final int GL_INTERNALFORMAT_GREEN_TYPE = GL43.GL_INTERNALFORMAT_GREEN_TYPE;
	public static final int GL_INTERNALFORMAT_PREFERRED = GL43.GL_INTERNALFORMAT_PREFERRED;
	public static final int GL_INTERNALFORMAT_RED_SIZE = GL43.GL_INTERNALFORMAT_RED_SIZE;
	public static final int GL_INTERNALFORMAT_RED_TYPE = GL43.GL_INTERNALFORMAT_RED_TYPE;
	public static final int GL_INTERNALFORMAT_SHARED_SIZE = GL43.GL_INTERNALFORMAT_SHARED_SIZE;
	public static final int GL_INTERNALFORMAT_STENCIL_SIZE = GL43.GL_INTERNALFORMAT_STENCIL_SIZE;
	public static final int GL_INTERNALFORMAT_STENCIL_TYPE = GL43.GL_INTERNALFORMAT_STENCIL_TYPE;
	public static final int GL_INTERNALFORMAT_SUPPORTED = GL43.GL_INTERNALFORMAT_SUPPORTED;
	public static final int GL_INTERPOLATE = GL13.GL_INTERPOLATE;
	public static final int GL_INVALID_ENUM = GL11.GL_INVALID_ENUM;
	public static final int GL_INVALID_FRAMEBUFFER_OPERATION = GL30.GL_INVALID_FRAMEBUFFER_OPERATION;
	public static final int GL_INVALID_INDEX = GL31.GL_INVALID_INDEX;
	public static final int GL_INVALID_OPERATION = GL11.GL_INVALID_OPERATION;
	public static final int GL_INVALID_VALUE = GL11.GL_INVALID_VALUE;
	public static final int GL_INVERT = GL11.GL_INVERT;
	public static final int GL_IS_PER_PATCH = GL43.GL_IS_PER_PATCH;
	public static final int GL_IS_ROW_MAJOR = GL43.GL_IS_ROW_MAJOR;
	public static final int GL_ISOLINES = GL40.GL_ISOLINES;
	
	//XXX K
	
	public static final int GL_KEEP = GL11.GL_KEEP;
	
	//XXX L
	
	public static final int GL_LAST_VERTEX_CONVENTION = GL41.GL_LAST_VERTEX_CONVENTION;
	public static final int GL_LAYER_PROVOKING_VERTEX = GL41.GL_LAYER_PROVOKING_VERTEX;
	public static final int GL_LEFT = GL11.GL_LEFT;
	public static final int GL_LEQUAL = GL11.GL_LEQUAL;
	public static final int GL_LESS = GL11.GL_LESS;
	public static final int GL_LIGHT_MODEL_AMBIENT = GL11.GL_LIGHT_MODEL_AMBIENT;
	public static final int GL_LIGHT_MODEL_COLOR_CONTROL = GL12.GL_LIGHT_MODEL_COLOR_CONTROL;
	public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = GL11.GL_LIGHT_MODEL_LOCAL_VIEWER;
	public static final int GL_LIGHT_MODEL_TWO_SIDE = GL11.GL_LIGHT_MODEL_TWO_SIDE;
	public static final int GL_LIGHT0 = GL11.GL_LIGHT0;
	public static final int GL_LIGHT1 = GL11.GL_LIGHT1;
	public static final int GL_LIGHT2 = GL11.GL_LIGHT2;
	public static final int GL_LIGHT3 = GL11.GL_LIGHT3;
	public static final int GL_LIGHT4 = GL11.GL_LIGHT4;
	public static final int GL_LIGHT5 = GL11.GL_LIGHT5;
	public static final int GL_LIGHT6 = GL11.GL_LIGHT6;
	public static final int GL_LIGHT7 = GL11.GL_LIGHT7;
	public static final int GL_LIGHTING = GL11.GL_LIGHTING;
	public static final int GL_LIGHTING_BIT = GL11.GL_LIGHTING_BIT;
	public static final int GL_LINE = GL11.GL_LINE;
	public static final int GL_LINE_BIT = GL11.GL_LINE_BIT;
	public static final int GL_LINE_LOOP = GL11.GL_LINE_LOOP;
	public static final int GL_LINE_RESET_TOKEN = GL11.GL_LINE_RESET_TOKEN;
	public static final int GL_LINE_SMOOTH = GL11.GL_LINE_SMOOTH;
	public static final int GL_LINE_SMOOTH_HINT = GL11.GL_LINE_SMOOTH_HINT;
	public static final int GL_LINE_STIPPLE = GL11.GL_LINE_STIPPLE;
	public static final int GL_LINE_STIPPLE_PATTERN = GL11.GL_LINE_STIPPLE_PATTERN;
	public static final int GL_LINE_STIPPLE_REPEAT = GL11.GL_LINE_STIPPLE_REPEAT;
	public static final int GL_LINE_STRIP = GL11.GL_LINE_STRIP;
	public static final int GL_LINE_STRIP_ADJACENCY = GL32.GL_LINE_STRIP_ADJACENCY;
	public static final int GL_LINE_TOKEN = GL11.GL_LINE_TOKEN;
	public static final int GL_LINE_WIDTH = GL11.GL_LINE_WIDTH;
	public static final int GL_LINE_WIDTH_GRANULARITY = GL11.GL_LINE_WIDTH_GRANULARITY;
	public static final int GL_LINE_WIDTH_RANGE = GL11.GL_LINE_WIDTH_RANGE;
	public static final int GL_LINEAR = GL11.GL_LINEAR;
	public static final int GL_LINEAR_ATTENUATION = GL11.GL_LINEAR_ATTENUATION;
	public static final int GL_LINEAR_MIPMAP_LINEAR = GL11.GL_LINEAR_MIPMAP_LINEAR;
	public static final int GL_LINEAR_MIPMAP_NEAREST = GL11.GL_LINEAR_MIPMAP_NEAREST;
	public static final int GL_LINES = GL11.GL_LINES;
	public static final int GL_LINES_ADJACENCY = GL32.GL_LINES_ADJACENCY;
	public static final int GL_LINK_STATUS = GL20.GL_LINK_STATUS;
	public static final int GL_LIST_BASE = GL11.GL_LIST_BASE;
	public static final int GL_LIST_BIT = GL11.GL_LIST_BIT;
	public static final int GL_LIST_INDEX = GL11.GL_LIST_INDEX;
	public static final int GL_LIST_MODE = GL11.GL_LIST_MODE;
	public static final int GL_LOAD = GL11.GL_LOAD;
	public static final int GL_LOCATION = GL43.GL_LOCATION;
	public static final int GL_LOCATION_COMPONENT = GL44.GL_LOCATION_COMPONENT;
	public static final int GL_LOCATION_INDEX = GL43.GL_LOCATION_INDEX;
	public static final int GL_LOGIC_OP = GL11.GL_LOGIC_OP;
	public static final int GL_LOGIC_OP_MODE = GL11.GL_LOGIC_OP_MODE;
	public static final int GL_LOW_FLOAT = GL41.GL_LOW_FLOAT;
	public static final int GL_LOW_INT = GL41.GL_LOW_INT;
	public static final int GL_LOWER_LEFT = GL20.GL_LOWER_LEFT;
	public static final int GL_LUMINANCE = GL11.GL_LUMINANCE;
	public static final int GL_LUMINANCE_ALPHA = GL11.GL_LUMINANCE_ALPHA;
	public static final int GL_LUMINANCE12 = GL11.GL_LUMINANCE12;
	public static final int GL_LUMINANCE12_ALPHA12 = GL11.GL_LUMINANCE12_ALPHA12;
	public static final int GL_LUMINANCE12_ALPHA4 = GL11.GL_LUMINANCE12_ALPHA4;
	public static final int GL_LUMINANCE16 = GL11.GL_LUMINANCE16;
	public static final int GL_LUMINANCE16_ALPHA16 = GL11.GL_LUMINANCE16_ALPHA16;
	public static final int GL_LUMINANCE4 = GL11.GL_LUMINANCE4;
	public static final int GL_LUMINANCE4_ALPHA4 = GL11.GL_LUMINANCE4_ALPHA4;
	public static final int GL_LUMINANCE6_ALPHA2 = GL11.GL_LUMINANCE6_ALPHA2;
	public static final int GL_LUMINANCE8 = GL11.GL_LUMINANCE8;
	public static final int GL_LUMINANCE8_ALPHA8 = GL11.GL_LUMINANCE8_ALPHA8;
	
	//XXX M
	
	public static final int GL_MAJOR_VERSION = GL30.GL_MAJOR_VERSION;
	public static final int GL_MANUAL_GENERATE_MIPMAP = GL43.GL_MANUAL_GENERATE_MIPMAP;
	public static final int GL_MAP_COHERENT_BIT = GL44.GL_MAP_COHERENT_BIT;
	public static final int GL_MAP_COLOR = GL11.GL_MAP_COLOR;
	public static final int GL_MAP_FLUSH_EXPLICIT_BIT = GL30.GL_MAP_FLUSH_EXPLICIT_BIT;
	public static final int GL_MAP_INVALIDATE_BUFFER_BIT = GL30.GL_MAP_INVALIDATE_BUFFER_BIT;
	public static final int GL_MAP_INVALIDATE_RANGE_BIT = GL30.GL_MAP_INVALIDATE_RANGE_BIT;
	public static final int GL_MAP_PERSISTENT_BIT = GL44.GL_MAP_PERSISTENT_BIT;
	public static final int GL_MAP_READ_BIT = GL30.GL_MAP_READ_BIT;
	public static final int GL_MAP_STENCIL = GL11.GL_MAP_STENCIL;
	public static final int GL_MAP_UNSYNCHRONIZED_BIT = GL30.GL_MAP_UNSYNCHRONIZED_BIT;
	public static final int GL_MAP_WRITE_BIT = GL30.GL_MAP_WRITE_BIT;
	public static final int GL_MAP1_COLOR_4 = GL11.GL_MAP1_COLOR_4;
	public static final int GL_MAP1_GRID_DOMAIN = GL11.GL_MAP1_GRID_DOMAIN;
	public static final int GL_MAP1_GRID_SEGMENTS = GL11.GL_MAP1_GRID_SEGMENTS;
	public static final int GL_MAP1_INDEX = GL11.GL_MAP1_INDEX;
	public static final int GL_MAP1_NORMAL = GL11.GL_MAP1_NORMAL;
	public static final int GL_MAP1_TEXTURE_COORD_1 = GL11.GL_MAP1_TEXTURE_COORD_1;
	public static final int GL_MAP1_TEXTURE_COORD_2 = GL11.GL_MAP1_TEXTURE_COORD_2;
	public static final int GL_MAP1_TEXTURE_COORD_3 = GL11.GL_MAP1_TEXTURE_COORD_3;
	public static final int GL_MAP1_TEXTURE_COORD_4 = GL11.GL_MAP1_TEXTURE_COORD_4;
	public static final int GL_MAP1_VERTEX_3 = GL11.GL_MAP1_VERTEX_3;
	public static final int GL_MAP1_VERTEX_4 = GL11.GL_MAP1_VERTEX_4;
	public static final int GL_MAP2_COLOR_4 = GL11.GL_MAP2_COLOR_4;
	public static final int GL_MAP2_GRID_DOMAIN = GL11.GL_MAP2_GRID_DOMAIN;
	public static final int GL_MAP2_GRID_SEGMENTS = GL11.GL_MAP2_GRID_SEGMENTS;
	public static final int GL_MAP2_INDEX = GL11.GL_MAP2_INDEX;
	public static final int GL_MAP2_NORMAL = GL11.GL_MAP2_NORMAL;
	public static final int GL_MAP2_TEXTURE_COORD_1 = GL11.GL_MAP2_TEXTURE_COORD_1;
	public static final int GL_MAP2_TEXTURE_COORD_2 = GL11.GL_MAP2_TEXTURE_COORD_2;
	public static final int GL_MAP2_TEXTURE_COORD_3 = GL11.GL_MAP2_TEXTURE_COORD_3;
	public static final int GL_MAP2_TEXTURE_COORD_4 = GL11.GL_MAP2_TEXTURE_COORD_4;
	public static final int GL_MAP2_VERTEX_3 = GL11.GL_MAP2_VERTEX_3;
	public static final int GL_MAP2_VERTEX_4 = GL11.GL_MAP2_VERTEX_4;
	public static final int GL_MATRIX_MODE = GL11.GL_MATRIX_MODE;
	public static final int GL_MATRIX_STRIDE = GL43.GL_MATRIX_STRIDE;
	public static final int GL_MAX = GL14.GL_MAX;
	public static final int GL_MAX_3D_TEXTURE_SIZE = GL12.GL_MAX_3D_TEXTURE_SIZE;
	public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = GL30.GL_MAX_ARRAY_TEXTURE_LAYERS;
	public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = GL42.GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS;
	public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = GL42.GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE;
	public static final int GL_MAX_ATTRIB_STACK_DEPTH = GL11.GL_MAX_ATTRIB_STACK_DEPTH;
	public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = GL11.GL_MAX_CLIENT_ATTRIB_STACK_DEPTH;
	public static final int GL_MAX_CLIP_DISTANCES = GL30.GL_MAX_CLIP_DISTANCES;
	public static final int GL_MAX_CLIP_PLANES = GL11.GL_MAX_CLIP_PLANES;
	public static final int GL_MAX_COLOR_ATTACHMENTS = GL30.GL_MAX_COLOR_ATTACHMENTS;
	public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = GL32.GL_MAX_COLOR_TEXTURE_SAMPLES;
	public static final int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_COMBINED_ATOMIC_COUNTERS = GL42.GL_MAX_COMBINED_ATOMIC_COUNTERS;
	public static final int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = GL43.GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMBINED_DIMENSIONS = GL43.GL_MAX_COMBINED_DIMENSIONS;
	public static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = GL31.GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = GL31.GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMBINED_IMAGE_UNIFORMS = GL42.GL_MAX_COMBINED_IMAGE_UNIFORMS;
	public static final int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS = GL42.GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS;
	public static final int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = GL43.GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES;
	public static final int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = GL40.GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = GL40.GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = GL31.GL_MAX_COMBINED_UNIFORM_BLOCKS;
	public static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = GL31.GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = GL43.GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_COMPUTE_ATOMIC_COUNTERS = GL43.GL_MAX_COMPUTE_ATOMIC_COUNTERS;
	public static final int GL_MAX_COMPUTE_IMAGE_UNIFORMS = GL43.GL_MAX_COMPUTE_IMAGE_UNIFORMS;
	public static final int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = GL43.GL_MAX_COMPUTE_SHARED_MEMORY_SIZE;
	public static final int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = GL43.GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_COMPUTE_UNIFORM_BLOCKS = GL43.GL_MAX_COMPUTE_UNIFORM_BLOCKS;
	public static final int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = GL43.GL_MAX_COMPUTE_UNIFORM_COMPONENTS;
	public static final int GL_MAX_COMPUTE_WORK_GROUP_COUNT = GL43.GL_MAX_COMPUTE_WORK_GROUP_COUNT;
	public static final int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = GL43.GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS;
	public static final int GL_MAX_COMPUTE_WORK_GROUP_SIZE = GL43.GL_MAX_COMPUTE_WORK_GROUP_SIZE;
	public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = GL13.GL_MAX_CUBE_MAP_TEXTURE_SIZE;
	public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = GL43.GL_MAX_DEBUG_GROUP_STACK_DEPTH;
	public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = GL43.GL_MAX_DEBUG_LOGGED_MESSAGES;
	public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = GL43.GL_MAX_DEBUG_MESSAGE_LENGTH;
	public static final int GL_MAX_DEPTH = GL43.GL_MAX_DEPTH;
	public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = GL32.GL_MAX_DEPTH_TEXTURE_SAMPLES;
	public static final int GL_MAX_DRAW_BUFFERS = GL20.GL_MAX_DRAW_BUFFERS;
	public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = GL33.GL_MAX_DUAL_SOURCE_DRAW_BUFFERS;
	public static final int GL_MAX_ELEMENT_INDEX = GL43.GL_MAX_ELEMENT_INDEX;
	public static final int GL_MAX_ELEMENTS_INDICES = GL12.GL_MAX_ELEMENTS_INDICES;
	public static final int GL_MAX_ELEMENTS_VERTICES = GL12.GL_MAX_ELEMENTS_VERTICES;
	public static final int GL_MAX_EVAL_ORDER = GL11.GL_MAX_EVAL_ORDER;
	public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = GL42.GL_MAX_FRAGMENT_ATOMIC_COUNTERS;
	public static final int GL_MAX_FRAGMENT_IMAGE_UNIFORMS = GL42.GL_MAX_FRAGMENT_IMAGE_UNIFORMS;
	public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = GL32.GL_MAX_FRAGMENT_INPUT_COMPONENTS;
	public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = GL40.GL_MAX_FRAGMENT_INTERPOLATION_OFFSET;
	public static final int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = GL31.GL_MAX_FRAGMENT_UNIFORM_BLOCKS;
	public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS;
	public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = GL41.GL_MAX_FRAGMENT_UNIFORM_VECTORS;
	public static final int GL_MAX_FRAMEBUFFER_HEIGHT = GL43.GL_MAX_FRAMEBUFFER_HEIGHT;
	public static final int GL_MAX_FRAMEBUFFER_LAYERS = GL43.GL_MAX_FRAMEBUFFER_LAYERS;
	public static final int GL_MAX_FRAMEBUFFER_SAMPLES = GL43.GL_MAX_FRAMEBUFFER_SAMPLES;
	public static final int GL_MAX_FRAMEBUFFER_WIDTH = GL43.GL_MAX_FRAMEBUFFER_WIDTH;
	public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = GL42.GL_MAX_GEOMETRY_ATOMIC_COUNTERS;
	public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS = GL42.GL_MAX_GEOMETRY_IMAGE_UNIFORMS;
	public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = GL32.GL_MAX_GEOMETRY_INPUT_COMPONENTS;
	public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = GL32.GL_MAX_GEOMETRY_OUTPUT_COMPONENTS;
	public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = GL32.GL_MAX_GEOMETRY_OUTPUT_VERTICES;
	public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS;
	public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = GL32.GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = GL32.GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS;
	public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS;
	public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = GL32.GL_MAX_GEOMETRY_UNIFORM_COMPONENTS;
	public static final int GL_MAX_HEIGHT = GL43.GL_MAX_HEIGHT;
	public static final int GL_MAX_IMAGE_SAMPLES = GL42.GL_MAX_IMAGE_SAMPLES;
	public static final int GL_MAX_IMAGE_UNITS = GL42.GL_MAX_IMAGE_UNITS;
	public static final int GL_MAX_INTEGER_SAMPLES = GL32.GL_MAX_INTEGER_SAMPLES;
	public static final int GL_MAX_LABEL_LENGTH = GL43.GL_MAX_LABEL_LENGTH;
	public static final int GL_MAX_LAYERS = GL43.GL_MAX_LAYERS;
	public static final int GL_MAX_LIGHTS = GL11.GL_MAX_LIGHTS;
	public static final int GL_MAX_LIST_NESTING = GL11.GL_MAX_LIST_NESTING;
	public static final int GL_MAX_MODELVIEW_STACK_DEPTH = GL11.GL_MAX_MODELVIEW_STACK_DEPTH;
	public static final int GL_MAX_NAME_LENGTH = GL43.GL_MAX_NAME_LENGTH;
	public static final int GL_MAX_NAME_STACK_DEPTH = GL11.GL_MAX_NAME_STACK_DEPTH;
	public static final int GL_MAX_NUM_ACTIVE_VARIABLES = GL43.GL_MAX_NUM_ACTIVE_VARIABLES;
	public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = GL43.GL_MAX_NUM_COMPATIBLE_SUBROUTINES;
	public static final int GL_MAX_PATCH_VERTICES = GL40.GL_MAX_PATCH_VERTICES;
	public static final int GL_MAX_PIXEL_MAP_TABLE = GL11.GL_MAX_PIXEL_MAP_TABLE;
	public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = GL30.GL_MAX_PROGRAM_TEXEL_OFFSET;
	public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS_ARB = GL40.GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS_ARB;
	public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = GL40.GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_ARB;
	public static final int GL_MAX_PROJECTION_STACK_DEPTH = GL11.GL_MAX_PROJECTION_STACK_DEPTH;
	public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE = GL31.GL_MAX_RECTANGLE_TEXTURE_SIZE;
	public static final int GL_MAX_RENDERBUFFER_SIZE = GL30.GL_MAX_RENDERBUFFER_SIZE;
	public static final int GL_MAX_SAMPLE_MASK_WORDS = GL32.GL_MAX_SAMPLE_MASK_WORDS;
	public static final int GL_MAX_SAMPLES = GL30.GL_MAX_SAMPLES;
	public static final int GL_MAX_SERVER_WAIT_TIMEOUT = GL32.GL_MAX_SERVER_WAIT_TIMEOUT;
	public static final int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE;
	public static final int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = GL43.GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS;
	public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = GL40.GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS;
	public static final int GL_MAX_SUBROUTINES = GL40.GL_MAX_SUBROUTINES;
	public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = GL42.GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS;
	public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = GL42.GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS;
	public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = GL40.GL_MAX_TESS_CONTROL_INPUT_COMPONENTS;
	public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = GL40.GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS;
	public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = GL40.GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = GL40.GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS;
	public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = GL40.GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS;
	public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = GL40.GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS;
	public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = GL42.GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS;
	public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = GL42.GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS;
	public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = GL40.GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS;
	public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = GL40.GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS;
	public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = GL40.GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = GL40.GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS;
	public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = GL40.GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS;
	public static final int GL_MAX_TESS_GEN_LEVEL = GL40.GL_MAX_TESS_GEN_LEVEL;
	public static final int GL_MAX_TESS_PATCH_COMPONENTS = GL40.GL_MAX_TESS_PATCH_COMPONENTS;
	public static final int GL_MAX_TEXTURE_BUFFER_SIZE = GL31.GL_MAX_TEXTURE_BUFFER_SIZE;
	public static final int GL_MAX_TEXTURE_COORDS = GL20.GL_MAX_TEXTURE_COORDS;
	public static final int GL_MAX_TEXTURE_IMAGE_UNITS = GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_TEXTURE_LOD_BIAS = GL14.GL_MAX_TEXTURE_LOD_BIAS;
	public static final int GL_MAX_TEXTURE_SIZE = GL11.GL_MAX_TEXTURE_SIZE;
	public static final int GL_MAX_TEXTURE_STACK_DEPTH = GL11.GL_MAX_TEXTURE_STACK_DEPTH;
	public static final int GL_MAX_TEXTURE_UNITS = GL13.GL_MAX_TEXTURE_UNITS;
	public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = GL40.GL_MAX_TRANSFORM_FEEDBACK_BUFFERS;
	public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = GL30.GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS;
	public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = GL30.GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS;
	public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = GL30.GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS;
	public static final int GL_MAX_UNIFORM_BLOCK_SIZE = GL31.GL_MAX_UNIFORM_BLOCK_SIZE;
	public static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS;
	public static final int GL_MAX_UNIFORM_LOCATIONS = GL43.GL_MAX_UNIFORM_LOCATIONS;
	public static final int GL_MAX_VARYING_COMPONENTS = GL32.GL_MAX_VARYING_COMPONENTS;
	public static final int GL_MAX_VARYING_FLOATS = GL20.GL_MAX_VARYING_FLOATS;
	public static final int GL_MAX_VARYING_VECTORS = GL41.GL_MAX_VARYING_VECTORS;
	public static final int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = GL42.GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS;
	public static final int GL_MAX_VERTEX_ATOMIC_COUNTERS = GL42.GL_MAX_VERTEX_ATOMIC_COUNTERS;
	public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = GL43.GL_MAX_VERTEX_ATTRIB_BINDINGS;
	public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = GL43.GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET;
	public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = GL44.GL_MAX_VERTEX_ATTRIB_STRIDE;
	public static final int GL_MAX_VERTEX_ATTRIBS = GL20.GL_MAX_VERTEX_ATTRIBS;
	public static final int GL_MAX_VERTEX_IMAGE_UNIFORMS = GL42.GL_MAX_VERTEX_IMAGE_UNIFORMS;
	public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = GL32.GL_MAX_VERTEX_OUTPUT_COMPONENTS;
	public static final int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = GL43.GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS;
	public static final int GL_MAX_VERTEX_STREAMS = GL40.GL_MAX_VERTEX_STREAMS;
	public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS;
	public static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = GL31.GL_MAX_VERTEX_UNIFORM_BLOCKS;
	public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS;
	public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = GL41.GL_MAX_VERTEX_UNIFORM_VECTORS;
	public static final int GL_MAX_VIEWPORT_DIMS = GL11.GL_MAX_VIEWPORT_DIMS;
	public static final int GL_MAX_VIEWPORTS = GL41.GL_MAX_VIEWPORTS;
	public static final int GL_MAX_WIDTH = GL43.GL_MAX_WIDTH;
	public static final int GL_MEDIUM_FLOAT = GL41.GL_MEDIUM_FLOAT;
	public static final int GL_MEDIUM_INT = GL41.GL_MEDIUM_INT;
	public static final int GL_MIN = GL14.GL_MIN;
	public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = GL40.GL_MIN_FRAGMENT_INTERPOLATION_OFFSET;
	public static final int GL_MIN_MAP_BUFFER_ALIGNMENT = GL42.GL_MIN_MAP_BUFFER_ALIGNMENT;
	public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = GL30.GL_MIN_PROGRAM_TEXEL_OFFSET;
	public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = GL40.GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_ARB;
	public static final int GL_MIN_SAMPLE_SHADING_VALUE = GL40.GL_MIN_SAMPLE_SHADING_VALUE;
	public static final int GL_MINOR_VERSION = GL30.GL_MINOR_VERSION;
	public static final int GL_MIPMAP = GL43.GL_MIPMAP;
	public static final int GL_MIRROR_CLAMP_TO_EDGE = GL44.GL_MIRROR_CLAMP_TO_EDGE;
	public static final int GL_MIRRORED_REPEAT = GL14.GL_MIRRORED_REPEAT;
	public static final int GL_MODELVIEW = GL11.GL_MODELVIEW;
	public static final int GL_MODELVIEW_MATRIX = GL11.GL_MODELVIEW_MATRIX;
	public static final int GL_MODELVIEW_STACK_DEPTH = GL11.GL_MODELVIEW_STACK_DEPTH;
	public static final int GL_MODULATE = GL11.GL_MODULATE;
	public static final int GL_MULT = GL11.GL_MULT;
	public static final int GL_MULTISAMPLE = GL13.GL_MULTISAMPLE;
	public static final int GL_MULTISAMPLE_BIT = GL13.GL_MULTISAMPLE_BIT;
	
	//XXX N
	
	public static final int GL_N3F_V3F = GL11.GL_N3F_V3F;
	public static final int GL_NAME_LENGTH = GL43.GL_NAME_LENGTH;
	public static final int GL_NAME_STACK_DEPTH = GL11.GL_NAME_STACK_DEPTH;
	public static final int GL_NAND = GL11.GL_NAND;
	public static final int GL_NEAREST = GL11.GL_NEAREST;
	public static final int GL_NEAREST_MIPMAP_LINEAR = GL11.GL_NEAREST_MIPMAP_LINEAR;
	public static final int GL_NEAREST_MIPMAP_NEAREST = GL11.GL_NEAREST_MIPMAP_NEAREST;
	public static final int GL_NEVER = GL11.GL_NEVER;
	public static final int GL_NICEST = GL11.GL_NICEST;
	public static final int GL_NO_ERROR = GL11.GL_NO_ERROR;
	public static final int GL_NONE = GL11.GL_NONE;
	public static final int GL_NOOP = GL11.GL_NOOP;
	public static final int GL_NOR = GL11.GL_NOR;
	public static final int GL_NORMAL_ARRAY = GL11.GL_NORMAL_ARRAY;
	public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = GL15.GL_NORMAL_ARRAY_BUFFER_BINDING;
	public static final int GL_NORMAL_ARRAY_POINTER = GL11.GL_NORMAL_ARRAY_POINTER;
	public static final int GL_NORMAL_ARRAY_STRIDE = GL11.GL_NORMAL_ARRAY_STRIDE;
	public static final int GL_NORMAL_ARRAY_TYPE = GL11.GL_NORMAL_ARRAY_TYPE;
	public static final int GL_NORMAL_MAP = GL13.GL_NORMAL_MAP;
	public static final int GL_NORMALIZE = GL11.GL_NORMALIZE;
	public static final int GL_NOTEQUAL = GL11.GL_NOTEQUAL;
	public static final int GL_NUM_ACTIVE_VARIABLES = GL43.GL_NUM_ACTIVE_VARIABLES;
	public static final int GL_NUM_COMPATIBLE_SUBROUTINES = GL40.GL_NUM_COMPATIBLE_SUBROUTINES;
	public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = GL13.GL_NUM_COMPRESSED_TEXTURE_FORMATS;
	public static final int GL_NUM_EXTENSIONS = GL30.GL_NUM_EXTENSIONS;
	public static final int GL_NUM_PROGRAM_BINARY_FORMATS = GL41.GL_NUM_PROGRAM_BINARY_FORMATS;
	public static final int GL_NUM_SAMPLE_COUNTS = GL43.GL_NUM_SAMPLE_COUNTS;
	public static final int GL_NUM_SHADER_BINARY_FORMATS = GL41.GL_NUM_SHADER_BINARY_FORMATS;
	public static final int GL_NUM_SHADING_LANGUAGE_VERSIONS = GL43.GL_NUM_SHADING_LANGUAGE_VERSIONS;
	
	//XXX O
	
	public static final int GL_OBJECT_LINEAR = GL11.GL_OBJECT_LINEAR;
	public static final int GL_OBJECT_PLANE = GL11.GL_OBJECT_PLANE;
	public static final int GL_OBJECT_TYPE = GL32.GL_OBJECT_TYPE;
	public static final int GL_OFFSET = GL43.GL_OFFSET;
	public static final int GL_ONE = GL11.GL_ONE;
	public static final int GL_ONE_MINUS_CONSTANT_ALPHA = GL11.GL_ONE_MINUS_CONSTANT_ALPHA;
	public static final int GL_ONE_MINUS_CONSTANT_COLOR = GL11.GL_ONE_MINUS_CONSTANT_COLOR;
	public static final int GL_ONE_MINUS_DST_ALPHA = GL11.GL_ONE_MINUS_DST_ALPHA;
	public static final int GL_ONE_MINUS_DST_COLOR = GL11.GL_ONE_MINUS_DST_COLOR;
	public static final int GL_ONE_MINUS_SRC_ALPHA = GL11.GL_ONE_MINUS_SRC_ALPHA;
	public static final int GL_ONE_MINUS_SRC_COLOR = GL11.GL_ONE_MINUS_SRC_COLOR;
	public static final int GL_ONE_MINUS_SRC1_ALPHA = GL33.GL_ONE_MINUS_SRC1_ALPHA;
	public static final int GL_ONE_MINUS_SRC1_COLOR = GL33.GL_ONE_MINUS_SRC1_COLOR;
	public static final int GL_OPERAND0_ALPHA = GL13.GL_OPERAND0_ALPHA;
	public static final int GL_OPERAND0_RGB = GL13.GL_OPERAND0_RGB;
	public static final int GL_OPERAND1_ALPHA = GL13.GL_OPERAND1_ALPHA;
	public static final int GL_OPERAND1_RGB = GL13.GL_OPERAND1_RGB;
	public static final int GL_OPERAND2_ALPHA = GL13.GL_OPERAND2_ALPHA;
	public static final int GL_OPERAND2_RGB = GL13.GL_OPERAND2_RGB;
	public static final int GL_OR = GL11.GL_OR;
	public static final int GL_OR_INVERTED = GL11.GL_OR_INVERTED;
	public static final int GL_OR_REVERSE = GL11.GL_OR_REVERSE;
	public static final int GL_ORDER = GL11.GL_ORDER;
	public static final int GL_OUT_OF_MEMORY = GL11.GL_OUT_OF_MEMORY;
	
	//XXX P
	
	public static final int GL_PACK_ALIGNMENT = GL11.GL_PACK_ALIGNMENT;
	public static final int GL_PACK_COMPRESSED_BLOCK_DEPTH = GL42.GL_PACK_COMPRESSED_BLOCK_DEPTH;
	public static final int GL_PACK_COMPRESSED_BLOCK_HEIGHT = GL42.GL_PACK_COMPRESSED_BLOCK_HEIGHT;
	public static final int GL_PACK_COMPRESSED_BLOCK_SIZE = GL42.GL_PACK_COMPRESSED_BLOCK_SIZE;
	public static final int GL_PACK_COMPRESSED_BLOCK_WIDTH = GL42.GL_PACK_COMPRESSED_BLOCK_WIDTH;
	public static final int GL_PACK_IMAGE_HEIGHT = GL12.GL_PACK_IMAGE_HEIGHT;
	public static final int GL_PACK_LSB_FIRST = GL11.GL_PACK_LSB_FIRST;
	public static final int GL_PACK_ROW_LENGTH = GL11.GL_PACK_ROW_LENGTH;
	public static final int GL_PACK_SKIP_IMAGES = GL12.GL_PACK_SKIP_IMAGES;
	public static final int GL_PACK_SKIP_PIXELS = GL11.GL_PACK_SKIP_PIXELS;
	public static final int GL_PACK_SKIP_ROWS = GL11.GL_PACK_SKIP_ROWS;
	public static final int GL_PACK_SWAP_BYTES = GL11.GL_PACK_SWAP_BYTES;
	public static final int GL_PASS_THROUGH_TOKEN = GL11.GL_PASS_THROUGH_TOKEN;
	public static final int GL_PATCH_DEFAULT_INNER_LEVEL = GL40.GL_PATCH_DEFAULT_INNER_LEVEL;
	public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = GL40.GL_PATCH_DEFAULT_OUTER_LEVEL;
	public static final int GL_PATCH_VERTICES = GL40.GL_PATCH_VERTICES;
	public static final int GL_PATCHES = GL40.GL_PATCHES;
	public static final int GL_PERSPECTIVE_CORRECTION_HINT = GL11.GL_PERSPECTIVE_CORRECTION_HINT;
	public static final int GL_PIXEL_BUFFER_BARRIER_BIT = GL42.GL_PIXEL_BUFFER_BARRIER_BIT;
	public static final int GL_PIXEL_MAP_A_TO_A = GL11.GL_PIXEL_MAP_A_TO_A;
	public static final int GL_PIXEL_MAP_A_TO_A_SIZE = GL11.GL_PIXEL_MAP_A_TO_A_SIZE;
	public static final int GL_PIXEL_MAP_B_TO_B = GL11.GL_PIXEL_MAP_B_TO_B;
	public static final int GL_PIXEL_MAP_B_TO_B_SIZE = GL11.GL_PIXEL_MAP_B_TO_B_SIZE;
	public static final int GL_PIXEL_MAP_G_TO_G = GL11.GL_PIXEL_MAP_G_TO_G;
	public static final int GL_PIXEL_MAP_G_TO_G_SIZE = GL11.GL_PIXEL_MAP_G_TO_G_SIZE;
	public static final int GL_PIXEL_MAP_I_TO_A = GL11.GL_PIXEL_MAP_I_TO_A;
	public static final int GL_PIXEL_MAP_I_TO_A_SIZE = GL11.GL_PIXEL_MAP_I_TO_A_SIZE;
	public static final int GL_PIXEL_MAP_I_TO_B = GL11.GL_PIXEL_MAP_I_TO_B;
	public static final int GL_PIXEL_MAP_I_TO_B_SIZE = GL11.GL_PIXEL_MAP_I_TO_B_SIZE;
	public static final int GL_PIXEL_MAP_I_TO_G = GL11.GL_PIXEL_MAP_I_TO_G;
	public static final int GL_PIXEL_MAP_I_TO_G_SIZE = GL11.GL_PIXEL_MAP_I_TO_G_SIZE;
	public static final int GL_PIXEL_MAP_I_TO_I = GL11.GL_PIXEL_MAP_I_TO_I;
	public static final int GL_PIXEL_MAP_I_TO_I_SIZE = GL11.GL_PIXEL_MAP_I_TO_I_SIZE;
	public static final int GL_PIXEL_MAP_I_TO_R = GL11.GL_PIXEL_MAP_I_TO_R;
	public static final int GL_PIXEL_MAP_I_TO_R_SIZE = GL11.GL_PIXEL_MAP_I_TO_R_SIZE;
	public static final int GL_PIXEL_MAP_R_TO_R = GL11.GL_PIXEL_MAP_R_TO_R;
	public static final int GL_PIXEL_MAP_R_TO_R_SIZE = GL11.GL_PIXEL_MAP_R_TO_R_SIZE;
	public static final int GL_PIXEL_MAP_S_TO_S = GL11.GL_PIXEL_MAP_S_TO_S;
	public static final int GL_PIXEL_MAP_S_TO_S_SIZE = GL11.GL_PIXEL_MAP_S_TO_S_SIZE;
	public static final int GL_PIXEL_MODE_BIT = GL11.GL_PIXEL_MODE_BIT;
	public static final int GL_PIXEL_PACK_BUFFER = GL21.GL_PIXEL_PACK_BUFFER;
	public static final int GL_PIXEL_PACK_BUFFER_BINDING = GL21.GL_PIXEL_PACK_BUFFER_BINDING;
	public static final int GL_PIXEL_UNPACK_BUFFER = GL21.GL_PIXEL_UNPACK_BUFFER;
	public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = GL21.GL_PIXEL_UNPACK_BUFFER_BINDING;
	public static final int GL_POINT = GL11.GL_POINT;
	public static final int GL_POINT_BIT = GL11.GL_POINT_BIT;
	public static final int GL_POINT_DISTANCE_ATTENUATION = GL14.GL_POINT_DISTANCE_ATTENUATION;
	public static final int GL_POINT_FADE_THRESHOLD_SIZE = GL14.GL_POINT_FADE_THRESHOLD_SIZE;
	public static final int GL_POINT_SIZE = GL11.GL_POINT_SIZE;
	public static final int GL_POINT_SIZE_GRANULARITY = GL11.GL_POINT_SIZE_GRANULARITY;
	public static final int GL_POINT_SIZE_MAX = GL14.GL_POINT_SIZE_MAX;
	public static final int GL_POINT_SIZE_MIN = GL14.GL_POINT_SIZE_MIN;
	public static final int GL_POINT_SIZE_RANGE = GL11.GL_POINT_SIZE_RANGE;
	public static final int GL_POINT_SMOOTH = GL11.GL_POINT_SMOOTH;
	public static final int GL_POINT_SMOOTH_HINT = GL11.GL_POINT_SMOOTH_HINT;
	public static final int GL_POINT_SPRITE = GL20.GL_POINT_SPRITE;
	public static final int GL_POINT_SPRITE_COORD_ORIGIN = GL20.GL_POINT_SPRITE_COORD_ORIGIN;
	public static final int GL_POINT_TOKEN = GL11.GL_POINT_TOKEN;
	public static final int GL_POINTS = GL11.GL_POINTS;
	public static final int GL_POLYGON = GL11.GL_POLYGON;
	public static final int GL_POLYGON_BIT = GL11.GL_POLYGON_BIT;
	public static final int GL_POLYGON_MODE = GL11.GL_POLYGON_MODE;
	public static final int GL_POLYGON_OFFSET_FACTOR = GL11.GL_POLYGON_OFFSET_FACTOR;
	public static final int GL_POLYGON_OFFSET_FILL = GL11.GL_POLYGON_OFFSET_FILL;
	public static final int GL_POLYGON_OFFSET_LINE = GL11.GL_POLYGON_OFFSET_LINE;
	public static final int GL_POLYGON_OFFSET_POINT = GL11.GL_POLYGON_OFFSET_POINT;
	public static final int GL_POLYGON_OFFSET_UNITS = GL11.GL_POLYGON_OFFSET_UNITS;
	public static final int GL_POLYGON_SMOOTH = GL11.GL_POLYGON_SMOOTH;
	public static final int GL_POLYGON_SMOOTH_HINT = GL11.GL_POLYGON_SMOOTH_HINT;
	public static final int GL_POLYGON_STIPPLE = GL11.GL_POLYGON_STIPPLE;
	public static final int GL_POLYGON_STIPPLE_BIT = GL11.GL_POLYGON_STIPPLE_BIT;
	public static final int GL_POLYGON_TOKEN = GL11.GL_POLYGON_TOKEN;
	public static final int GL_POSITION = GL11.GL_POSITION;
	public static final int GL_PREVIOUS = GL13.GL_PREVIOUS;
	public static final int GL_PRIMARY_COLOR = GL13.GL_PRIMARY_COLOR;
	public static final int GL_PRIMITIVE_RESTART = GL31.GL_PRIMITIVE_RESTART;
	public static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = GL43.GL_PRIMITIVE_RESTART_FIXED_INDEX;
	public static final int GL_PRIMITIVE_RESTART_INDEX = GL31.GL_PRIMITIVE_RESTART_INDEX;
	public static final int GL_PRIMITIVES_GENERATED = GL30.GL_PRIMITIVES_GENERATED;
	public static final int GL_PROGRAM = GL43.GL_PROGRAM;
	public static final int GL_PROGRAM_BINARY_FORMATS = GL41.GL_PROGRAM_BINARY_FORMATS;
	public static final int GL_PROGRAM_BINARY_LENGTH = GL41.GL_PROGRAM_BINARY_LENGTH;
	public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = GL41.GL_PROGRAM_BINARY_RETRIEVABLE_HINT;
	public static final int GL_PROGRAM_INPUT = GL43.GL_PROGRAM_INPUT;
	public static final int GL_PROGRAM_OUTPUT = GL43.GL_PROGRAM_OUTPUT;
	public static final int GL_PROGRAM_PIPELINE = GL43.GL_PROGRAM_PIPELINE;
	public static final int GL_PROGRAM_PIPELINE_BINDING = GL41.GL_PROGRAM_PIPELINE_BINDING;
	public static final int GL_PROGRAM_POINT_SIZE = GL32.GL_PROGRAM_POINT_SIZE;
	public static final int GL_PROGRAM_SEPARABLE = GL41.GL_PROGRAM_SEPARABLE;
	public static final int GL_PROJECTION = GL11.GL_PROJECTION;
	public static final int GL_PROJECTION_MATRIX = GL11.GL_PROJECTION_MATRIX;
	public static final int GL_PROJECTION_STACK_DEPTH = GL11.GL_PROJECTION_STACK_DEPTH;
	public static final int GL_PROVOKING_VERTEX = GL41.GL_PROVOKING_VERTEX;
	public static final int GL_PROXY_TEXTURE_1D = GL11.GL_PROXY_TEXTURE_1D;
	public static final int GL_PROXY_TEXTURE_1D_ARRAY = GL30.GL_PROXY_TEXTURE_1D_ARRAY;
	public static final int GL_PROXY_TEXTURE_2D = GL11.GL_PROXY_TEXTURE_2D;
	public static final int GL_PROXY_TEXTURE_2D_ARRAY = GL30.GL_PROXY_TEXTURE_2D_ARRAY;
	public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = GL32.GL_PROXY_TEXTURE_2D_MULTISAMPLE;
	public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = GL32.GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY;
	public static final int GL_PROXY_TEXTURE_3D = GL12.GL_PROXY_TEXTURE_3D;
	public static final int GL_PROXY_TEXTURE_CUBE_MAP = GL13.GL_PROXY_TEXTURE_CUBE_MAP;
	public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = GL40.GL_PROXY_TEXTURE_CUBE_MAP_ARRAY;
	public static final int GL_PROXY_TEXTURE_RECTANGLE = GL31.GL_PROXY_TEXTURE_RECTANGLE;
	
	//XXX Q
	
	public static final int GL_Q = GL11.GL_Q;
	public static final int GL_QUAD_STRIP = GL11.GL_QUAD_STRIP;
	public static final int GL_QUADRATIC_ATTENUATION = GL11.GL_QUADRATIC_ATTENUATION;
	public static final int GL_QUADS = GL11.GL_QUADS;
	public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = GL32.GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION;
	public static final int GL_QUERY = GL43.GL_QUERY;
	public static final int GL_QUERY_BUFFER = GL44.GL_QUERY_BUFFER;
	public static final int GL_QUERY_BUFFER_BARRIER_BIT = GL44.GL_QUERY_BUFFER_BARRIER_BIT;
	public static final int GL_QUERY_BUFFER_BINDING = GL44.GL_QUERY_BUFFER_BINDING;
	public static final int GL_QUERY_BY_REGION_NO_WAIT = GL30.GL_QUERY_BY_REGION_NO_WAIT;
	public static final int GL_QUERY_BY_REGION_WAIT = GL30.GL_QUERY_BY_REGION_WAIT;
	public static final int GL_QUERY_COUNTER_BITS = GL15.GL_QUERY_COUNTER_BITS;
	public static final int GL_QUERY_NO_WAIT = GL30.GL_QUERY_NO_WAIT;
	public static final int GL_QUERY_RESULT = GL15.GL_QUERY_RESULT;
	public static final int GL_QUERY_RESULT_AVAILABLE = GL15.GL_QUERY_RESULT_AVAILABLE;
	public static final int GL_QUERY_RESULT_NO_WAIT = GL44.GL_QUERY_RESULT_NO_WAIT;
	public static final int GL_QUERY_WAIT = GL30.GL_QUERY_WAIT;
	//XXX R
	public static final int GL_R = GL11.GL_R;
	public static final int GL_R11F_G11F_B10F = GL30.GL_R11F_G11F_B10F;
	public static final int GL_R16 = GL30.GL_R16;
	public static final int GL_R16_SNORM = GL31.GL_R16_SNORM;
	public static final int GL_R16F = GL30.GL_R16F;
	public static final int GL_R16I = GL30.GL_R16I;
	public static final int GL_R16UI = GL30.GL_R16UI;
	public static final int GL_R3_G3_B2 = GL11.GL_R3_G3_B2;
	public static final int GL_R32F = GL30.GL_R32F;
	public static final int GL_R32I = GL30.GL_R32I;
	public static final int GL_R32UI = GL30.GL_R32UI;
	public static final int GL_R8 = GL30.GL_R8;
	public static final int GL_R8_SNORM = GL31.GL_R8_SNORM;
	public static final int GL_R8I = GL30.GL_R8I;
	public static final int GL_R8UI = GL30.GL_R8UI;
	public static final int GL_RASTERIZER_DISCARD = GL30.GL_RASTERIZER_DISCARD;
	public static final int GL_READ_BUFFER = GL11.GL_READ_BUFFER;
	public static final int GL_READ_FRAMEBUFFER = GL30.GL_READ_FRAMEBUFFER;
	public static final int GL_READ_FRAMEBUFFER_BINDING = GL30.GL_READ_FRAMEBUFFER_BINDING;
	public static final int GL_READ_ONLY = GL15.GL_READ_ONLY;
	public static final int GL_READ_PIXELS = GL43.GL_READ_PIXELS;
	public static final int GL_READ_PIXELS_FORMAT = GL43.GL_READ_PIXELS_FORMAT;
	public static final int GL_READ_PIXELS_TYPE = GL43.GL_READ_PIXELS_TYPE;
	public static final int GL_READ_WRITE = GL15.GL_READ_WRITE;
	public static final int GL_RED = GL11.GL_RED;
	public static final int GL_RED_BIAS = GL11.GL_RED_BIAS;
	public static final int GL_RED_BITS = GL11.GL_RED_BITS;
	public static final int GL_RED_INTEGER = GL30.GL_RED_INTEGER;
	public static final int GL_RED_SCALE = GL11.GL_RED_SCALE;
	public static final int GL_RED_SNORM = GL31.GL_RED_SNORM;
	public static final int GL_REFERENCED_BY_COMPUTE_SHADER = GL43.GL_REFERENCED_BY_COMPUTE_SHADER;
	public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = GL43.GL_REFERENCED_BY_FRAGMENT_SHADER;
	public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = GL43.GL_REFERENCED_BY_GEOMETRY_SHADER;
	public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = GL43.GL_REFERENCED_BY_TESS_CONTROL_SHADER;
	public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = GL43.GL_REFERENCED_BY_TESS_EVALUATION_SHADER;
	public static final int GL_REFERENCED_BY_VERTEX_SHADER = GL43.GL_REFERENCED_BY_VERTEX_SHADER;
	public static final int GL_REFLECTION_MAP = GL13.GL_REFLECTION_MAP;
	public static final int GL_RENDER = GL11.GL_RENDER;
	public static final int GL_RENDER_MODE = GL11.GL_RENDER_MODE;
	public static final int GL_RENDERBUFFER = GL43.GL_RENDERBUFFER;
	public static final int GL_RENDERBUFFER_ALPHA_SIZE = GL30.GL_RENDERBUFFER_ALPHA_SIZE;
	public static final int GL_RENDERBUFFER_BINDING = GL30.GL_RENDERBUFFER_BINDING;
	public static final int GL_RENDERBUFFER_BLUE_SIZE = GL30.GL_RENDERBUFFER_BLUE_SIZE;
	public static final int GL_RENDERBUFFER_DEPTH_SIZE = GL30.GL_RENDERBUFFER_DEPTH_SIZE;
	public static final int GL_RENDERBUFFER_GREEN_SIZE = GL30.GL_RENDERBUFFER_GREEN_SIZE;
	public static final int GL_RENDERBUFFER_HEIGHT = GL30.GL_RENDERBUFFER_HEIGHT;
	public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = GL30.GL_RENDERBUFFER_INTERNAL_FORMAT;
	public static final int GL_RENDERBUFFER_RED_SIZE = GL30.GL_RENDERBUFFER_RED_SIZE;
	public static final int GL_RENDERBUFFER_SAMPLES = GL30.GL_RENDERBUFFER_SAMPLES;
	public static final int GL_RENDERBUFFER_STENCIL_SIZE = GL30.GL_RENDERBUFFER_STENCIL_SIZE;
	public static final int GL_RENDERBUFFER_WIDTH = GL30.GL_RENDERBUFFER_WIDTH;
	public static final int GL_RENDERER = GL11.GL_RENDERER;
	public static final int GL_REPEAT = GL11.GL_REPEAT;
	public static final int GL_REPLACE = GL11.GL_REPLACE;
	public static final int GL_RESCALE_NORMAL = GL12.GL_RESCALE_NORMAL;
	public static final int GL_RETURN = GL11.GL_RETURN;
	public static final int GL_RG = GL30.GL_RG;
	public static final int GL_RG_INTEGER = GL30.GL_RG_INTEGER;
	public static final int GL_RG_SNORM = GL31.GL_RG_SNORM;
	public static final int GL_RG16 = GL30.GL_RG16;
	public static final int GL_RG16_SNORM = GL31.GL_RG16_SNORM;
	public static final int GL_RG16F = GL30.GL_RG16F;
	public static final int GL_RG16I = GL30.GL_RG16I;
	public static final int GL_RG16UI = GL30.GL_RG16UI;
	public static final int GL_RG32F = GL30.GL_RG32F;
	public static final int GL_RG32I = GL30.GL_RG32I;
	public static final int GL_RG32UI = GL30.GL_RG32UI;
	public static final int GL_RG8 = GL30.GL_RG8;
	public static final int GL_RG8_SNORM = GL31.GL_RG8_SNORM;
	public static final int GL_RG8I = GL30.GL_RG8I;
	public static final int GL_RG8UI = GL30.GL_RG8UI;
	public static final int GL_RGB = GL11.GL_RGB;
	public static final int GL_RGB_INTEGER = GL30.GL_RGB_INTEGER;
	public static final int GL_RGB_SCALE = GL13.GL_RGB_SCALE;
	public static final int GL_RGB_SNORM = GL31.GL_RGB_SNORM;
	public static final int GL_RGB10 = GL11.GL_RGB10;
	public static final int GL_RGB10_A2 = GL11.GL_RGB10_A2;
	public static final int GL_RGB10_A2UI = GL33.GL_RGB10_A2UI;
	public static final int GL_RGB12 = GL11.GL_RGB12;
	public static final int GL_RGB16 = GL11.GL_RGB16;
	public static final int GL_RGB16_SNORM = GL31.GL_RGB16_SNORM;
	public static final int GL_RGB16F = GL30.GL_RGB16F;
	public static final int GL_RGB16I = GL30.GL_RGB16I;
	public static final int GL_RGB16UI = GL30.GL_RGB16UI;
	public static final int GL_RGB32F = GL30.GL_RGB32F;
	public static final int GL_RGB32I = GL30.GL_RGB32I;
	public static final int GL_RGB32UI = GL30.GL_RGB32UI;
	public static final int GL_RGB4 = GL11.GL_RGB4;
	public static final int GL_RGB5 = GL11.GL_RGB5;
	public static final int GL_RGB5_A1 = GL11.GL_RGB5_A1;
	public static final int GL_RGB565 = GL41.GL_RGB565;
	public static final int GL_RGB8 = GL11.GL_RGB8;
	public static final int GL_RGB8_SNORM = GL31.GL_RGB8_SNORM;
	public static final int GL_RGB8I = GL30.GL_RGB8I;
	public static final int GL_RGB8UI = GL30.GL_RGB8UI;
	public static final int GL_RGB9_E5 = GL30.GL_RGB9_E5;
	public static final int GL_RGBA = GL11.GL_RGBA;
	public static final int GL_RGBA_INTEGER = GL30.GL_RGBA_INTEGER;
	public static final int GL_RGBA_INTEGER_MODE = GL30.GL_RGBA_INTEGER_MODE;
	public static final int GL_RGBA_MODE = GL11.GL_RGBA_MODE;
	public static final int GL_RGBA_SNORM = GL31.GL_RGBA_SNORM;
	public static final int GL_RGBA12 = GL11.GL_RGBA12;
	public static final int GL_RGBA16 = GL11.GL_RGBA16;
	public static final int GL_RGBA16_SNORM = GL31.GL_RGBA16_SNORM;
	public static final int GL_RGBA16F = GL30.GL_RGBA16F;
	public static final int GL_RGBA16I = GL30.GL_RGBA16I;
	public static final int GL_RGBA16UI = GL30.GL_RGBA16UI;
	public static final int GL_RGBA2 = GL11.GL_RGBA2;
	public static final int GL_RGBA32F = GL30.GL_RGBA32F;
	public static final int GL_RGBA32I = GL30.GL_RGBA32I;
	public static final int GL_RGBA32UI = GL30.GL_RGBA32UI;
	public static final int GL_RGBA4 = GL11.GL_RGBA4;
	public static final int GL_RGBA8 = GL11.GL_RGBA8;
	public static final int GL_RGBA8_SNORM = GL31.GL_RGBA8_SNORM;
	public static final int GL_RGBA8I = GL30.GL_RGBA8I;
	public static final int GL_RGBA8UI = GL30.GL_RGBA8UI;
	public static final int GL_RIGHT = GL11.GL_RIGHT;
	
	//XXX S
	
	public static final int GL_S = GL11.GL_S;
	public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = GL13.GL_SAMPLE_ALPHA_TO_COVERAGE;
	public static final int GL_SAMPLE_ALPHA_TO_ONE = GL13.GL_SAMPLE_ALPHA_TO_ONE;
	public static final int GL_SAMPLE_BUFFERS = GL13.GL_SAMPLE_BUFFERS;
	public static final int GL_SAMPLE_COVERAGE = GL13.GL_SAMPLE_COVERAGE;
	public static final int GL_SAMPLE_COVERAGE_INVERT = GL13.GL_SAMPLE_COVERAGE_INVERT;
	public static final int GL_SAMPLE_COVERAGE_VALUE = GL13.GL_SAMPLE_COVERAGE_VALUE;
	public static final int GL_SAMPLE_MASK = GL32.GL_SAMPLE_MASK;
	public static final int GL_SAMPLE_MASK_VALUE = GL32.GL_SAMPLE_MASK_VALUE;
	public static final int GL_SAMPLE_POSITION = GL32.GL_SAMPLE_POSITION;
	public static final int GL_SAMPLE_SHADING = GL40.GL_SAMPLE_SHADING;
	public static final int GL_SAMPLER = GL43.GL_SAMPLER;
	public static final int GL_SAMPLER_1D = GL20.GL_SAMPLER_1D;
	public static final int GL_SAMPLER_1D_ARRAY = GL30.GL_SAMPLER_1D_ARRAY;
	public static final int GL_SAMPLER_1D_ARRAY_SHADOW = GL30.GL_SAMPLER_1D_ARRAY_SHADOW;
	public static final int GL_SAMPLER_1D_SHADOW = GL20.GL_SAMPLER_1D_SHADOW;
	public static final int GL_SAMPLER_2D = GL20.GL_SAMPLER_2D;
	public static final int GL_SAMPLER_2D_ARRAY = GL30.GL_SAMPLER_2D_ARRAY;
	public static final int GL_SAMPLER_2D_ARRAY_SHADOW = GL30.GL_SAMPLER_2D_ARRAY_SHADOW;
	public static final int GL_SAMPLER_2D_MULTISAMPLE = GL32.GL_SAMPLER_2D_MULTISAMPLE;
	public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = GL32.GL_SAMPLER_2D_MULTISAMPLE_ARRAY;
	public static final int GL_SAMPLER_2D_RECT = GL31.GL_SAMPLER_2D_RECT;
	public static final int GL_SAMPLER_2D_RECT_SHADOW = GL31.GL_SAMPLER_2D_RECT_SHADOW;
	public static final int GL_SAMPLER_2D_SHADOW = GL20.GL_SAMPLER_2D_SHADOW;
	public static final int GL_SAMPLER_3D = GL20.GL_SAMPLER_3D;
	public static final int GL_SAMPLER_BINDING = GL33.GL_SAMPLER_BINDING;
	public static final int GL_SAMPLER_BUFFER = GL30.GL_SAMPLER_BUFFER;
	public static final int GL_SAMPLER_CUBE = GL20.GL_SAMPLER_CUBE;
	public static final int GL_SAMPLER_CUBE_MAP_ARRAY = GL40.GL_SAMPLER_CUBE_MAP_ARRAY;
	public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = GL40.GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW;
	public static final int GL_SAMPLER_CUBE_SHADOW = GL30.GL_SAMPLER_CUBE_SHADOW;
	public static final int GL_SAMPLES = GL43.GL_SAMPLES;
	public static final int GL_SAMPLES_PASSED = GL15.GL_SAMPLES_PASSED;
	public static final int GL_SCISSOR_BIT = GL11.GL_SCISSOR_BIT;
	public static final int GL_SCISSOR_BOX = GL41.GL_SCISSOR_BOX;
	public static final int GL_SCISSOR_TEST = GL41.GL_SCISSOR_TEST;
	public static final int GL_SECONDARY_COLOR_ARRAY = GL14.GL_SECONDARY_COLOR_ARRAY;
	public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = GL15.GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING;
	public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = GL14.GL_SECONDARY_COLOR_ARRAY_POINTER;
	public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = GL14.GL_SECONDARY_COLOR_ARRAY_SIZE;
	public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = GL14.GL_SECONDARY_COLOR_ARRAY_STRIDE;
	public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = GL14.GL_SECONDARY_COLOR_ARRAY_TYPE;
	public static final int GL_SELECT = GL11.GL_SELECT;
	public static final int GL_SELECTION_BUFFER_POINTER = GL11.GL_SELECTION_BUFFER_POINTER;
	public static final int GL_SELECTION_BUFFER_SIZE = GL11.GL_SELECTION_BUFFER_SIZE;
	public static final int GL_SEPARATE_ATTRIBS = GL30.GL_SEPARATE_ATTRIBS;
	public static final int GL_SEPARATE_SPECULAR_COLOR = GL12.GL_SEPARATE_SPECULAR_COLOR;
	public static final int GL_SET = GL11.GL_SET;
	public static final int GL_SHADE_MODEL = GL11.GL_SHADE_MODEL;
	public static final int GL_SHADER = GL43.GL_SHADER;
	public static final int GL_SHADER_COMPILER = GL41.GL_SHADER_COMPILER;
	public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = GL42.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT;
	public static final int GL_SHADER_IMAGE_ATOMIC = GL43.GL_SHADER_IMAGE_ATOMIC;
	public static final int GL_SHADER_IMAGE_LOAD = GL43.GL_SHADER_IMAGE_LOAD;
	public static final int GL_SHADER_IMAGE_STORE = GL43.GL_SHADER_IMAGE_STORE;
	public static final int GL_SHADER_OBJECT = GL20.GL_SHADER_OBJECT;
	public static final int GL_SHADER_SOURCE_LENGTH = GL20.GL_SHADER_SOURCE_LENGTH;
	public static final int GL_SHADER_STORAGE_BARRIER_BIT = GL43.GL_SHADER_STORAGE_BARRIER_BIT;
	public static final int GL_SHADER_STORAGE_BLOCK = GL43.GL_SHADER_STORAGE_BLOCK;
	public static final int GL_SHADER_STORAGE_BUFFER = GL43.GL_SHADER_STORAGE_BUFFER;
	public static final int GL_SHADER_STORAGE_BUFFER_BINDING = GL43.GL_SHADER_STORAGE_BUFFER_BINDING;
	public static final int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = GL43.GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT;
	public static final int GL_SHADER_STORAGE_BUFFER_SIZE = GL43.GL_SHADER_STORAGE_BUFFER_SIZE;
	public static final int GL_SHADER_STORAGE_BUFFER_START = GL43.GL_SHADER_STORAGE_BUFFER_START;
	public static final int GL_SHADER_TYPE = GL20.GL_SHADER_TYPE;
	public static final int GL_SHADING_LANGUAGE_VERSION = GL20.GL_SHADING_LANGUAGE_VERSION;
	public static final int GL_SHININESS = GL11.GL_SHININESS;
	public static final int GL_SHORT = GL11.GL_SHORT;
	public static final int GL_SIGNALED = GL32.GL_SIGNALED;
	public static final int GL_SIGNED_NORMALIZED = GL31.GL_SIGNED_NORMALIZED;
	public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = GL43.GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST;
	public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = GL43.GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE;
	public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = GL43.GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST;
	public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = GL43.GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE;
	public static final int GL_SINGLE_COLOR = GL12.GL_SINGLE_COLOR;
	public static final int GL_SLUMINANCE = GL21.GL_SLUMINANCE;
	public static final int GL_SLUMINANCE_ALPHA = GL21.GL_SLUMINANCE_ALPHA;
	public static final int GL_SLUMINANCE8 = GL21.GL_SLUMINANCE8;
	public static final int GL_SLUMINANCE8_ALPHA8 = GL21.GL_SLUMINANCE8_ALPHA8;
	public static final int GL_SMOOTH = GL11.GL_SMOOTH;
	public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = GL12.GL_SMOOTH_LINE_WIDTH_GRANULARITY;
	public static final int GL_SMOOTH_LINE_WIDTH_RANGE = GL12.GL_SMOOTH_LINE_WIDTH_RANGE;
	public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = GL12.GL_SMOOTH_POINT_SIZE_GRANULARITY;
	public static final int GL_SMOOTH_POINT_SIZE_RANGE = GL12.GL_SMOOTH_POINT_SIZE_RANGE;
	public static final int GL_SOURCE0_ALPHA = GL13.GL_SOURCE0_ALPHA;
	public static final int GL_SOURCE0_RGB = GL13.GL_SOURCE0_RGB;
	public static final int GL_SOURCE1_ALPHA = GL13.GL_SOURCE1_ALPHA;
	public static final int GL_SOURCE1_RGB = GL13.GL_SOURCE1_RGB;
	public static final int GL_SOURCE2_ALPHA = GL13.GL_SOURCE2_ALPHA;
	public static final int GL_SOURCE2_RGB = GL13.GL_SOURCE2_RGB;
	public static final int GL_SPECULAR = GL11.GL_SPECULAR;
	public static final int GL_SPHERE_MAP = GL11.GL_SPHERE_MAP;
	public static final int GL_SPOT_CUTOFF = GL11.GL_SPOT_CUTOFF;
	public static final int GL_SPOT_DIRECTION = GL11.GL_SPOT_DIRECTION;
	public static final int GL_SPOT_EXPONENT = GL11.GL_SPOT_EXPONENT;
	public static final int GL_SRC_ALPHA = GL11.GL_SRC_ALPHA;
	public static final int GL_SRC_ALPHA_SATURATE = GL11.GL_SRC_ALPHA_SATURATE;
	public static final int GL_SRC_COLOR = GL11.GL_SRC_COLOR;
	public static final int GL_SRC0_ALPHA = GL15.GL_SRC0_ALPHA;
	public static final int GL_SRC0_RGB = GL15.GL_SRC0_RGB;
	public static final int GL_SRC1_ALPHA = GL33.GL_SRC1_ALPHA;
	public static final int GL_SRC1_COLOR = GL33.GL_SRC1_COLOR;
	public static final int GL_SRC1_RGB = GL15.GL_SRC1_RGB;
	public static final int GL_SRC2_ALPHA = GL15.GL_SRC2_ALPHA;
	public static final int GL_SRC2_RGB = GL15.GL_SRC2_RGB;
	public static final int GL_SRGB = GL21.GL_SRGB;
	public static final int GL_SRGB_ALPHA = GL21.GL_SRGB_ALPHA;
	public static final int GL_SRGB_DECODE_ARB = GL43.GL_SRGB_DECODE_ARB;
	public static final int GL_SRGB_READ = GL43.GL_SRGB_READ;
	public static final int GL_SRGB_WRITE = GL43.GL_SRGB_WRITE;
	public static final int GL_SRGB8 = GL21.GL_SRGB8;
	public static final int GL_SRGB8_ALPHA8 = GL21.GL_SRGB8_ALPHA8;
	public static final int GL_STACK_OVERFLOW = GL43.GL_STACK_OVERFLOW;
	public static final int GL_STACK_UNDERFLOW = GL43.GL_STACK_UNDERFLOW;
	public static final int GL_STATIC_COPY = GL15.GL_STATIC_COPY;
	public static final int GL_STATIC_DRAW = GL15.GL_STATIC_DRAW;
	public static final int GL_STATIC_READ = GL15.GL_STATIC_READ;	
	public static final int GL_STENCIL = GL11.GL_STENCIL;
	public static final int GL_STENCIL_ATTACHMENT = GL30.GL_STENCIL_ATTACHMENT;
	public static final int GL_STENCIL_BACK_FAIL = GL20.GL_STENCIL_BACK_FAIL;
	public static final int GL_STENCIL_BACK_FUNC = GL20.GL_STENCIL_BACK_FUNC;
	public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = GL20.GL_STENCIL_BACK_PASS_DEPTH_FAIL;
	public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = GL20.GL_STENCIL_BACK_PASS_DEPTH_PASS;
	public static final int GL_STENCIL_BACK_REF = GL20.GL_STENCIL_BACK_REF;
	public static final int GL_STENCIL_BACK_VALUE_MASK = GL20.GL_STENCIL_BACK_VALUE_MASK;
	public static final int GL_STENCIL_BACK_WRITEMASK = GL20.GL_STENCIL_BACK_WRITEMASK;
	public static final int GL_STENCIL_BITS = GL11.GL_STENCIL_BITS;
	public static final int GL_STENCIL_BUFFER = GL30.GL_STENCIL_BUFFER;
	public static final int GL_STENCIL_BUFFER_BIT = GL11.GL_STENCIL_BUFFER_BIT;
	public static final int GL_STENCIL_CLEAR_VALUE = GL11.GL_STENCIL_CLEAR_VALUE;
	public static final int GL_STENCIL_COMPONENTS = GL43.GL_STENCIL_COMPONENTS;
	public static final int GL_STENCIL_FAIL = GL11.GL_STENCIL_FAIL;
	public static final int GL_STENCIL_FUNC = GL11.GL_STENCIL_FUNC;
	public static final int GL_STENCIL_INDEX = GL11.GL_STENCIL_INDEX;
	public static final int GL_STENCIL_INDEX1 = GL30.GL_STENCIL_INDEX1;
	public static final int GL_STENCIL_INDEX16 = GL30.GL_STENCIL_INDEX16;
	public static final int GL_STENCIL_INDEX4 = GL30.GL_STENCIL_INDEX4;
	public static final int GL_STENCIL_INDEX8 = GL30.GL_STENCIL_INDEX8;
	public static final int GL_STENCIL_PASS_DEPTH_FAIL = GL11.GL_STENCIL_PASS_DEPTH_FAIL;
	public static final int GL_STENCIL_PASS_DEPTH_PASS = GL11.GL_STENCIL_PASS_DEPTH_PASS;
	public static final int GL_STENCIL_REF = GL11.GL_STENCIL_REF;
	public static final int GL_STENCIL_RENDERABLE = GL43.GL_STENCIL_RENDERABLE;
	public static final int GL_STENCIL_TEST = GL11.GL_STENCIL_TEST;
	public static final int GL_STENCIL_VALUE_MASK = GL11.GL_STENCIL_VALUE_MASK;
	public static final int GL_STENCIL_WRITEMASK = GL11.GL_STENCIL_WRITEMASK;
	public static final int GL_STEREO = GL11.GL_STEREO;
	public static final int GL_STREAM_COPY = GL15.GL_STREAM_COPY;
	public static final int GL_STREAM_DRAW = GL15.GL_STREAM_DRAW;
	public static final int GL_STREAM_READ = GL15.GL_STREAM_READ;
	public static final int GL_SUBPIXEL_BITS = GL11.GL_SUBPIXEL_BITS;
	public static final int GL_SUBTRACT = GL13.GL_SUBTRACT;
	public static final int GL_SYNC_CONDITION = GL32.GL_SYNC_CONDITION;
	public static final int GL_SYNC_FENCE = GL32.GL_SYNC_FENCE;
	public static final int GL_SYNC_FLAGS = GL32.GL_SYNC_FLAGS;
	public static final int GL_SYNC_FLUSH_COMMANDS_BIT = GL32.GL_SYNC_FLUSH_COMMANDS_BIT;
	public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = GL32.GL_SYNC_GPU_COMMANDS_COMPLETE;
	public static final int GL_SYNC_STATUS = GL32.GL_SYNC_STATUS;
	
	//XXX T
	
	public static final int GL_T = GL11.GL_T;
	public static final int GL_T2F_C3F_V3F = GL11.GL_T2F_C3F_V3F;
	public static final int GL_T2F_C4F_N3F_V3F = GL11.GL_T2F_C4F_N3F_V3F;
	public static final int GL_T2F_C4UB_V3F = GL11.GL_T2F_C4UB_V3F;
	public static final int GL_T2F_N3F_V3F = GL11.GL_T2F_N3F_V3F;
	public static final int GL_T2F_V3F = GL11.GL_T2F_V3F;
	public static final int GL_T4F_C4F_N3F_V4F = GL11.GL_T4F_C4F_N3F_V4F;
	public static final int GL_T4F_V4F = GL11.GL_T4F_V4F;
	public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = GL40.GL_TESS_CONTROL_OUTPUT_VERTICES;
	public static final int GL_TESS_CONTROL_SHADER = GL40.GL_TESS_CONTROL_SHADER;
	public static final int GL_TESS_CONTROL_SHADER_BIT = GL41.GL_TESS_CONTROL_SHADER_BIT;
	public static final int GL_TESS_CONTROL_SUBROUTINE = GL43.GL_TESS_CONTROL_SUBROUTINE;
	public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = GL43.GL_TESS_CONTROL_SUBROUTINE_UNIFORM;
	public static final int GL_TESS_CONTROL_TEXTURE = GL43.GL_TESS_CONTROL_TEXTURE;
	public static final int GL_TESS_EVALUATION_SHADER = GL40.GL_TESS_EVALUATION_SHADER;
	public static final int GL_TESS_EVALUATION_SHADER_BIT = GL41.GL_TESS_EVALUATION_SHADER_BIT;
	public static final int GL_TESS_EVALUATION_SUBROUTINE = GL43.GL_TESS_EVALUATION_SUBROUTINE;
	public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = GL43.GL_TESS_EVALUATION_SUBROUTINE_UNIFORM;
	public static final int GL_TESS_EVALUATION_TEXTURE = GL43.GL_TESS_EVALUATION_TEXTURE;
	public static final int GL_TESS_GEN_MODE = GL40.GL_TESS_GEN_MODE;
	public static final int GL_TESS_GEN_POINT_MODE = GL40.GL_TESS_GEN_POINT_MODE;
	public static final int GL_TESS_GEN_SPACING = GL40.GL_TESS_GEN_SPACING;
	public static final int GL_TESS_GEN_VERTEX_ORDER = GL40.GL_TESS_GEN_VERTEX_ORDER;
	public static final int GL_TEXTURE = GL11.GL_TEXTURE;
	public static final int GL_TEXTURE_1D = GL43.GL_TEXTURE_1D;
	public static final int GL_TEXTURE_1D_ARRAY = GL43.GL_TEXTURE_1D_ARRAY;
	public static final int GL_TEXTURE_2D = GL43.GL_TEXTURE_2D;
	public static final int GL_TEXTURE_2D_ARRAY = GL43.GL_TEXTURE_2D_ARRAY;
	public static final int GL_TEXTURE_2D_MULTISAMPLE = GL43.GL_TEXTURE_2D_MULTISAMPLE;
	public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = GL43.GL_TEXTURE_2D_MULTISAMPLE_ARRAY;
	public static final int GL_TEXTURE_3D = GL43.GL_TEXTURE_3D;
	public static final int GL_TEXTURE_ALPHA_SIZE = GL11.GL_TEXTURE_ALPHA_SIZE;
	public static final int GL_TEXTURE_ALPHA_TYPE = GL30.GL_TEXTURE_ALPHA_TYPE;
	public static final int GL_TEXTURE_BASE_LEVEL = GL12.GL_TEXTURE_BASE_LEVEL;
	public static final int GL_TEXTURE_BINDING_1D = GL11.GL_TEXTURE_BINDING_1D;
	public static final int GL_TEXTURE_BINDING_1D_ARRAY = GL30.GL_TEXTURE_BINDING_1D_ARRAY;
	public static final int GL_TEXTURE_BINDING_2D = GL11.GL_TEXTURE_BINDING_2D;
	public static final int GL_TEXTURE_BINDING_2D_ARRAY = GL30.GL_TEXTURE_BINDING_2D_ARRAY;
	public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = GL32.GL_TEXTURE_BINDING_2D_MULTISAMPLE;
	public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = GL32.GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY;
	public static final int GL_TEXTURE_BINDING_3D = GL12.GL_TEXTURE_BINDING_3D;
	public static final int GL_TEXTURE_BINDING_BUFFER = GL31.GL_TEXTURE_BINDING_BUFFER;
	public static final int GL_TEXTURE_BINDING_CUBE_MAP = GL13.GL_TEXTURE_BINDING_CUBE_MAP;
	public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = GL40.GL_TEXTURE_BINDING_CUBE_MAP_ARRAY;
	public static final int GL_TEXTURE_BINDING_RECTANGLE = GL31.GL_TEXTURE_BINDING_RECTANGLE;
	public static final int GL_TEXTURE_BIT = GL11.GL_TEXTURE_BIT;
	public static final int GL_TEXTURE_BLUE_SIZE = GL11.GL_TEXTURE_BLUE_SIZE;
	public static final int GL_TEXTURE_BLUE_TYPE = GL30.GL_TEXTURE_BLUE_TYPE;
	public static final int GL_TEXTURE_BORDER = GL11.GL_TEXTURE_BORDER;
	public static final int GL_TEXTURE_BORDER_COLOR = GL11.GL_TEXTURE_BORDER_COLOR;
	public static final int GL_TEXTURE_BUFFER = GL43.GL_TEXTURE_BUFFER;
	public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = GL31.GL_TEXTURE_BUFFER_DATA_STORE_BINDING;
	public static final int GL_TEXTURE_BUFFER_FORMAT = GL31.GL_TEXTURE_BUFFER_FORMAT;
	public static final int GL_TEXTURE_BUFFER_OFFSET = GL43.GL_TEXTURE_BUFFER_OFFSET;
	public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = GL43.GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT;
	public static final int GL_TEXTURE_BUFFER_SIZE = GL43.GL_TEXTURE_BUFFER_SIZE;
	public static final int GL_TEXTURE_COMPARE_FUNC = GL14.GL_TEXTURE_COMPARE_FUNC;
	public static final int GL_TEXTURE_COMPARE_MODE = GL14.GL_TEXTURE_COMPARE_MODE;
	public static final int GL_TEXTURE_COMPONENTS = GL11.GL_TEXTURE_COMPONENTS;
	public static final int GL_TEXTURE_COMPRESSED = GL43.GL_TEXTURE_COMPRESSED;
	public static final int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = GL43.GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT;
	public static final int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = GL43.GL_TEXTURE_COMPRESSED_BLOCK_SIZE;
	public static final int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = GL43.GL_TEXTURE_COMPRESSED_BLOCK_WIDTH;
	public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = GL13.GL_TEXTURE_COMPRESSED_IMAGE_SIZE;
	public static final int GL_TEXTURE_COMPRESSION_HINT = GL13.GL_TEXTURE_COMPRESSION_HINT;
	public static final int GL_TEXTURE_COORD_ARRAY = GL11.GL_TEXTURE_COORD_ARRAY;
	public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = GL15.GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING;
	public static final int GL_TEXTURE_COORD_ARRAY_POINTER = GL11.GL_TEXTURE_COORD_ARRAY_POINTER;
	public static final int GL_TEXTURE_COORD_ARRAY_SIZE = GL11.GL_TEXTURE_COORD_ARRAY_SIZE;
	public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = GL11.GL_TEXTURE_COORD_ARRAY_STRIDE;
	public static final int GL_TEXTURE_COORD_ARRAY_TYPE = GL11.GL_TEXTURE_COORD_ARRAY_TYPE;
	public static final int GL_TEXTURE_CUBE_MAP = GL43.GL_TEXTURE_CUBE_MAP;
	public static final int GL_TEXTURE_CUBE_MAP_ARRAY = GL43.GL_TEXTURE_CUBE_MAP_ARRAY;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
	public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;
	public static final int GL_TEXTURE_DEPTH = GL12.GL_TEXTURE_DEPTH;
	public static final int GL_TEXTURE_DEPTH_SIZE = GL14.GL_TEXTURE_DEPTH_SIZE;
	public static final int GL_TEXTURE_DEPTH_TYPE = GL30.GL_TEXTURE_DEPTH_TYPE;
	public static final int GL_TEXTURE_ENV = GL11.GL_TEXTURE_ENV;
	public static final int GL_TEXTURE_ENV_COLOR = GL11.GL_TEXTURE_ENV_COLOR;
	public static final int GL_TEXTURE_ENV_MODE = GL11.GL_TEXTURE_ENV_MODE;
	public static final int GL_TEXTURE_FETCH_BARRIER_BIT = GL42.GL_TEXTURE_FETCH_BARRIER_BIT;
	public static final int GL_TEXTURE_FILTER_CONTROL = GL14.GL_TEXTURE_FILTER_CONTROL;
	public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = GL32.GL_TEXTURE_FIXED_SAMPLE_LOCATIONS;
	public static final int GL_TEXTURE_GATHER = GL43.GL_TEXTURE_GATHER;
	public static final int GL_TEXTURE_GATHER_SHADOW = GL43.GL_TEXTURE_GATHER_SHADOW;
	public static final int GL_TEXTURE_GEN_MODE = GL11.GL_TEXTURE_GEN_MODE;
	public static final int GL_TEXTURE_GEN_Q = GL11.GL_TEXTURE_GEN_Q;
	public static final int GL_TEXTURE_GEN_R = GL11.GL_TEXTURE_GEN_R;
	public static final int GL_TEXTURE_GEN_S = GL11.GL_TEXTURE_GEN_S;
	public static final int GL_TEXTURE_GEN_T = GL11.GL_TEXTURE_GEN_T;
	public static final int GL_TEXTURE_GREEN_SIZE = GL11.GL_TEXTURE_GREEN_SIZE;
	public static final int GL_TEXTURE_GREEN_TYPE = GL30.GL_TEXTURE_GREEN_TYPE;
	public static final int GL_TEXTURE_HEIGHT = GL11.GL_TEXTURE_HEIGHT;
	public static final int GL_TEXTURE_IMAGE_FORMAT = GL43.GL_TEXTURE_IMAGE_FORMAT;
	public static final int GL_TEXTURE_IMAGE_TYPE = GL43.GL_TEXTURE_IMAGE_TYPE;
	public static final int GL_TEXTURE_IMMUTABLE_FORMAT = GL42.GL_TEXTURE_IMMUTABLE_FORMAT;
	public static final int GL_TEXTURE_IMMUTABLE_LEVELS = GL43.GL_TEXTURE_IMMUTABLE_LEVELS;
	public static final int GL_TEXTURE_INTENSITY_SIZE = GL11.GL_TEXTURE_INTENSITY_SIZE;
	public static final int GL_TEXTURE_INTENSITY_TYPE = GL30.GL_TEXTURE_INTENSITY_TYPE;
	public static final int GL_TEXTURE_INTERNAL_FORMAT = GL11.GL_TEXTURE_INTERNAL_FORMAT;
	public static final int GL_TEXTURE_LOD_BIAS = GL14.GL_TEXTURE_LOD_BIAS;
	public static final int GL_TEXTURE_LUMINANCE_SIZE = GL11.GL_TEXTURE_LUMINANCE_SIZE;
	public static final int GL_TEXTURE_LUMINANCE_TYPE = GL30.GL_TEXTURE_LUMINANCE_TYPE;
	public static final int GL_TEXTURE_MAG_FILTER = GL11.GL_TEXTURE_MAG_FILTER;
	public static final int GL_TEXTURE_MATRIX = GL11.GL_TEXTURE_MATRIX;
	public static final int GL_TEXTURE_MAX_LEVEL = GL12.GL_TEXTURE_MAX_LEVEL;
	public static final int GL_TEXTURE_MAX_LOD = GL12.GL_TEXTURE_MAX_LOD;
	public static final int GL_TEXTURE_MIN_FILTER = GL11.GL_TEXTURE_MIN_FILTER;
	public static final int GL_TEXTURE_MIN_LOD = GL12.GL_TEXTURE_MIN_LOD;
	public static final int GL_TEXTURE_PRIORITY = GL11.GL_TEXTURE_PRIORITY;
	public static final int GL_TEXTURE_RECTANGLE = GL43.GL_TEXTURE_RECTANGLE;
	public static final int GL_TEXTURE_RED_SIZE = GL11.GL_TEXTURE_RED_SIZE;
	public static final int GL_TEXTURE_RED_TYPE = GL30.GL_TEXTURE_RED_TYPE;
	public static final int GL_TEXTURE_RESIDENT = GL11.GL_TEXTURE_RESIDENT;
	public static final int GL_TEXTURE_SAMPLES = GL32.GL_TEXTURE_SAMPLES;
	public static final int GL_TEXTURE_SHADOW = GL43.GL_TEXTURE_SHADOW;
	public static final int GL_TEXTURE_SHARED_SIZE = GL30.GL_TEXTURE_SHARED_SIZE;
	public static final int GL_TEXTURE_STACK_DEPTH = GL11.GL_TEXTURE_STACK_DEPTH;
	public static final int GL_TEXTURE_STENCIL_SIZE = GL30.GL_TEXTURE_STENCIL_SIZE;
	public static final int GL_TEXTURE_SWIZZLE_A = GL33.GL_TEXTURE_SWIZZLE_A;
	public static final int GL_TEXTURE_SWIZZLE_B = GL33.GL_TEXTURE_SWIZZLE_B;
	public static final int GL_TEXTURE_SWIZZLE_G = GL33.GL_TEXTURE_SWIZZLE_G;
	public static final int GL_TEXTURE_SWIZZLE_R = GL33.GL_TEXTURE_SWIZZLE_R;
	public static final int GL_TEXTURE_SWIZZLE_RGBA = GL33.GL_TEXTURE_SWIZZLE_RGBA;
	public static final int GL_TEXTURE_UPDATE_BARRIER_BIT = GL42.GL_TEXTURE_UPDATE_BARRIER_BIT;
	public static final int GL_TEXTURE_VIEW = GL43.GL_TEXTURE_VIEW;
	public static final int GL_TEXTURE_VIEW_MIN_LAYER = GL43.GL_TEXTURE_VIEW_MIN_LAYER;
	public static final int GL_TEXTURE_VIEW_MIN_LEVEL = GL43.GL_TEXTURE_VIEW_MIN_LEVEL;
	public static final int GL_TEXTURE_VIEW_NUM_LAYERS = GL43.GL_TEXTURE_VIEW_NUM_LAYERS;
	public static final int GL_TEXTURE_VIEW_NUM_LEVELS = GL43.GL_TEXTURE_VIEW_NUM_LEVELS;
	public static final int GL_TEXTURE_WIDTH = GL11.GL_TEXTURE_WIDTH;
	public static final int GL_TEXTURE_WRAP_R = GL12.GL_TEXTURE_WRAP_R;
	public static final int GL_TEXTURE_WRAP_S = GL11.GL_TEXTURE_WRAP_S;
	public static final int GL_TEXTURE_WRAP_T = GL11.GL_TEXTURE_WRAP_T;
	public static final int GL_TEXTURE0 = GL13.GL_TEXTURE0;
	public static final int GL_TEXTURE1 = GL13.GL_TEXTURE1;
	public static final int GL_TEXTURE10 = GL13.GL_TEXTURE10;
	public static final int GL_TEXTURE11 = GL13.GL_TEXTURE11;
	public static final int GL_TEXTURE12 = GL13.GL_TEXTURE12;
	public static final int GL_TEXTURE13 = GL13.GL_TEXTURE13;
	public static final int GL_TEXTURE14 = GL13.GL_TEXTURE14;
	public static final int GL_TEXTURE15 = GL13.GL_TEXTURE15;
	public static final int GL_TEXTURE16 = GL13.GL_TEXTURE16;
	public static final int GL_TEXTURE17 = GL13.GL_TEXTURE17;
	public static final int GL_TEXTURE18 = GL13.GL_TEXTURE18;
	public static final int GL_TEXTURE19 = GL13.GL_TEXTURE19;
	public static final int GL_TEXTURE2 = GL13.GL_TEXTURE2;
	public static final int GL_TEXTURE20 = GL13.GL_TEXTURE20;
	public static final int GL_TEXTURE21 = GL13.GL_TEXTURE21;
	public static final int GL_TEXTURE22 = GL13.GL_TEXTURE22;
	public static final int GL_TEXTURE23 = GL13.GL_TEXTURE23;
	public static final int GL_TEXTURE24 = GL13.GL_TEXTURE24;
	public static final int GL_TEXTURE25 = GL13.GL_TEXTURE25;
	public static final int GL_TEXTURE26 = GL13.GL_TEXTURE26;
	public static final int GL_TEXTURE27 = GL13.GL_TEXTURE27;
	public static final int GL_TEXTURE28 = GL13.GL_TEXTURE28;
	public static final int GL_TEXTURE29 = GL13.GL_TEXTURE29;
	public static final int GL_TEXTURE3 = GL13.GL_TEXTURE3;
	public static final int GL_TEXTURE30 = GL13.GL_TEXTURE30;
	public static final int GL_TEXTURE31 = GL13.GL_TEXTURE31;
	public static final int GL_TEXTURE4 = GL13.GL_TEXTURE4;
	public static final int GL_TEXTURE5 = GL13.GL_TEXTURE5;
	public static final int GL_TEXTURE6 = GL13.GL_TEXTURE6;
	public static final int GL_TEXTURE7 = GL13.GL_TEXTURE7;
	public static final int GL_TEXTURE8 = GL13.GL_TEXTURE8;
	public static final int GL_TEXTURE9 = GL13.GL_TEXTURE9;
	public static final int GL_TIME_ELAPSED = GL33.GL_TIME_ELAPSED;
	public static final int GL_TIMEOUT_EXPIRED = GL32.GL_TIMEOUT_EXPIRED;
	public static final int GL_TIMESTAMP = GL33.GL_TIMESTAMP;
	public static final int GL_TOP_LEVEL_ARRAY_SIZE = GL43.GL_TOP_LEVEL_ARRAY_SIZE;
	public static final int GL_TOP_LEVEL_ARRAY_STRIDE = GL43.GL_TOP_LEVEL_ARRAY_STRIDE;
	public static final int GL_TRANSFORM_BIT = GL11.GL_TRANSFORM_BIT;
	public static final int GL_TRANSFORM_FEEDBACK = GL40.GL_TRANSFORM_FEEDBACK;
	public static final int GL_TRANSFORM_FEEDBACK_ACTIVE = GL40.GL_TRANSFORM_FEEDBACK_ACTIVE;
	public static final int GL_TRANSFORM_FEEDBACK_BARRIER_BIT = GL42.GL_TRANSFORM_FEEDBACK_BARRIER_BIT;
	public static final int GL_TRANSFORM_FEEDBACK_BINDING = GL40.GL_TRANSFORM_FEEDBACK_BINDING;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER = GL30.GL_TRANSFORM_FEEDBACK_BUFFER;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = GL40.GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = GL30.GL_TRANSFORM_FEEDBACK_BUFFER_BINDING;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = GL44.GL_TRANSFORM_FEEDBACK_BUFFER_INDEX;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = GL30.GL_TRANSFORM_FEEDBACK_BUFFER_MODE;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = GL40.GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = GL30.GL_TRANSFORM_FEEDBACK_BUFFER_SIZE;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = GL30.GL_TRANSFORM_FEEDBACK_BUFFER_START;
	public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = GL44.GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE;
	public static final int GL_TRANSFORM_FEEDBACK_PAUSED = GL40.GL_TRANSFORM_FEEDBACK_PAUSED;
	public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = GL30.GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN;
	public static final int GL_TRANSFORM_FEEDBACK_VARYING = GL43.GL_TRANSFORM_FEEDBACK_VARYING;
	public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = GL30.GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH;
	public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = GL30.GL_TRANSFORM_FEEDBACK_VARYINGS;
	public static final int GL_TRANSPOSE_COLOR_MATRIX = GL13.GL_TRANSPOSE_COLOR_MATRIX;
	public static final int GL_TRANSPOSE_MODELVIEW_MATRIX = GL13.GL_TRANSPOSE_MODELVIEW_MATRIX;
	public static final int GL_TRANSPOSE_PROJECTION_MATRIX = GL13.GL_TRANSPOSE_PROJECTION_MATRIX;
	public static final int GL_TRANSPOSE_TEXTURE_MATRIX = GL13.GL_TRANSPOSE_TEXTURE_MATRIX;
	public static final int GL_TRIANGLE_FAN = GL11.GL_TRIANGLE_FAN;
	public static final int GL_TRIANGLE_STRIP = GL11.GL_TRIANGLE_STRIP;
	public static final int GL_TRIANGLE_STRIP_ADJACENCY = GL32.GL_TRIANGLE_STRIP_ADJACENCY;
	public static final int GL_TRIANGLES = GL11.GL_TRIANGLES;
	public static final int GL_TRIANGLES_ADJACENCY = GL32.GL_TRIANGLES_ADJACENCY;
	public static final int GL_TRUE = GL11.GL_TRUE;
	public static final int GL_TYPE = GL43.GL_TYPE;
	
	//XXX U
	
	public static final int GL_UNDEFINED_VERTEX = GL41.GL_UNDEFINED_VERTEX;
	public static final int GL_UNIFORM = GL43.GL_UNIFORM;
	public static final int GL_UNIFORM_ARRAY_STRIDE = GL31.GL_UNIFORM_ARRAY_STRIDE;
	public static final int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = GL42.GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX;
	public static final int GL_UNIFORM_BARRIER_BIT = GL42.GL_UNIFORM_BARRIER_BIT;
	public static final int GL_UNIFORM_BLOCK = GL43.GL_UNIFORM_BLOCK;
	public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = GL31.GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES;
	public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = GL31.GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS;
	public static final int GL_UNIFORM_BLOCK_BINDING = GL31.GL_UNIFORM_BLOCK_BINDING;
	public static final int GL_UNIFORM_BLOCK_DATA_SIZE = GL31.GL_UNIFORM_BLOCK_DATA_SIZE;
	public static final int GL_UNIFORM_BLOCK_INDEX = GL31.GL_UNIFORM_BLOCK_INDEX;
	public static final int GL_UNIFORM_BLOCK_NAME_LENGTH = GL31.GL_UNIFORM_BLOCK_NAME_LENGTH;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = GL43.GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = GL31.GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = GL31.GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = GL40.GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = GL40.GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER;
	public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = GL31.GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER;
	public static final int GL_UNIFORM_BUFFER = GL31.GL_UNIFORM_BUFFER;
	public static final int GL_UNIFORM_BUFFER_BINDING = GL31.GL_UNIFORM_BUFFER_BINDING;
	public static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = GL31.GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT;
	public static final int GL_UNIFORM_BUFFER_SIZE = GL31.GL_UNIFORM_BUFFER_SIZE;
	public static final int GL_UNIFORM_BUFFER_START = GL31.GL_UNIFORM_BUFFER_START;
	public static final int GL_UNIFORM_IS_ROW_MAJOR = GL31.GL_UNIFORM_IS_ROW_MAJOR;
	public static final int GL_UNIFORM_MATRIX_STRIDE = GL31.GL_UNIFORM_MATRIX_STRIDE;
	public static final int GL_UNIFORM_NAME_LENGTH = GL40.GL_UNIFORM_NAME_LENGTH;
	public static final int GL_UNIFORM_OFFSET = GL31.GL_UNIFORM_OFFSET;
	public static final int GL_UNIFORM_SIZE = GL40.GL_UNIFORM_SIZE;
	public static final int GL_UNIFORM_TYPE = GL31.GL_UNIFORM_TYPE;
	public static final int GL_UNPACK_ALIGNMENT = GL11.GL_UNPACK_ALIGNMENT;
	public static final int GL_UNPACK_COMPRESSED_BLOCK_DEPTH = GL42.GL_UNPACK_COMPRESSED_BLOCK_DEPTH;
	public static final int GL_UNPACK_COMPRESSED_BLOCK_HEIGHT = GL42.GL_UNPACK_COMPRESSED_BLOCK_HEIGHT;
	public static final int GL_UNPACK_COMPRESSED_BLOCK_SIZE = GL42.GL_UNPACK_COMPRESSED_BLOCK_SIZE;
	public static final int GL_UNPACK_COMPRESSED_BLOCK_WIDTH = GL42.GL_UNPACK_COMPRESSED_BLOCK_WIDTH;
	public static final int GL_UNPACK_IMAGE_HEIGHT = GL12.GL_UNPACK_IMAGE_HEIGHT;
	public static final int GL_UNPACK_LSB_FIRST = GL11.GL_UNPACK_LSB_FIRST;
	public static final int GL_UNPACK_ROW_LENGTH = GL11.GL_UNPACK_ROW_LENGTH;
	public static final int GL_UNPACK_SKIP_IMAGES = GL12.GL_UNPACK_SKIP_IMAGES;
	public static final int GL_UNPACK_SKIP_PIXELS = GL11.GL_UNPACK_SKIP_PIXELS;
	public static final int GL_UNPACK_SKIP_ROWS = GL11.GL_UNPACK_SKIP_ROWS;
	public static final int GL_UNPACK_SWAP_BYTES = GL11.GL_UNPACK_SWAP_BYTES;
	public static final int GL_UNSIGNALED = GL32.GL_UNSIGNALED;
	public static final int GL_UNSIGNED_BYTE = GL11.GL_UNSIGNED_BYTE;
	public static final int GL_UNSIGNED_BYTE_2_3_3_REV = GL12.GL_UNSIGNED_BYTE_2_3_3_REV;
	public static final int GL_UNSIGNED_BYTE_3_3_2 = GL12.GL_UNSIGNED_BYTE_3_3_2;
	public static final int GL_UNSIGNED_INT = GL11.GL_UNSIGNED_INT;
	public static final int GL_UNSIGNED_INT_10_10_10_2 = GL12.GL_UNSIGNED_INT_10_10_10_2;
	public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = GL30.GL_UNSIGNED_INT_10F_11F_11F_REV;
	public static final int GL_UNSIGNED_INT_2_10_10_10_REV = GL12.GL_UNSIGNED_INT_2_10_10_10_REV;
	public static final int GL_UNSIGNED_INT_24_8 = GL30.GL_UNSIGNED_INT_24_8;
	public static final int GL_UNSIGNED_INT_5_9_9_9_REV = GL30.GL_UNSIGNED_INT_5_9_9_9_REV;
	public static final int GL_UNSIGNED_INT_8_8_8_8 = GL12.GL_UNSIGNED_INT_8_8_8_8;
	public static final int GL_UNSIGNED_INT_8_8_8_8_REV = GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
	public static final int GL_UNSIGNED_INT_ATOMIC_COUNTER = GL42.GL_UNSIGNED_INT_ATOMIC_COUNTER;
	public static final int GL_UNSIGNED_INT_IMAGE_1D = GL42.GL_UNSIGNED_INT_IMAGE_1D;
	public static final int GL_UNSIGNED_INT_IMAGE_1D_ARRAY = GL42.GL_UNSIGNED_INT_IMAGE_1D_ARRAY;
	public static final int GL_UNSIGNED_INT_IMAGE_2D = GL42.GL_UNSIGNED_INT_IMAGE_2D;
	public static final int GL_UNSIGNED_INT_IMAGE_2D_ARRAY = GL42.GL_UNSIGNED_INT_IMAGE_2D_ARRAY;
	public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE = GL42.GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE;
	public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY = GL42.GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY;
	public static final int GL_UNSIGNED_INT_IMAGE_2D_RECT = GL42.GL_UNSIGNED_INT_IMAGE_2D_RECT;
	public static final int GL_UNSIGNED_INT_IMAGE_3D = GL42.GL_UNSIGNED_INT_IMAGE_3D;
	public static final int GL_UNSIGNED_INT_IMAGE_BUFFER = GL42.GL_UNSIGNED_INT_IMAGE_BUFFER;
	public static final int GL_UNSIGNED_INT_IMAGE_CUBE = GL42.GL_UNSIGNED_INT_IMAGE_CUBE;
	public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = GL42.GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY;
	public static final int GL_UNSIGNED_INT_SAMPLER_1D = GL30.GL_UNSIGNED_INT_SAMPLER_1D;
	public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = GL30.GL_UNSIGNED_INT_SAMPLER_1D_ARRAY;
	public static final int GL_UNSIGNED_INT_SAMPLER_2D = GL30.GL_UNSIGNED_INT_SAMPLER_2D;
	public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = GL30.GL_UNSIGNED_INT_SAMPLER_2D_ARRAY;
	public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = GL32.GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE;
	public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = GL32.GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY;
	public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT = GL30.GL_UNSIGNED_INT_SAMPLER_2D_RECT;
	public static final int GL_UNSIGNED_INT_SAMPLER_3D = GL30.GL_UNSIGNED_INT_SAMPLER_3D;
	public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER = GL30.GL_UNSIGNED_INT_SAMPLER_BUFFER;
	public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = GL30.GL_UNSIGNED_INT_SAMPLER_CUBE;
	public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = GL40.GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY;
	public static final int GL_UNSIGNED_INT_VEC2 = GL30.GL_UNSIGNED_INT_VEC2;
	public static final int GL_UNSIGNED_INT_VEC3 = GL30.GL_UNSIGNED_INT_VEC3;
	public static final int GL_UNSIGNED_INT_VEC4 = GL30.GL_UNSIGNED_INT_VEC4;
	public static final int GL_UNSIGNED_NORMALIZED = GL30.GL_UNSIGNED_NORMALIZED;
	public static final int GL_UNSIGNED_SHORT = GL11.GL_UNSIGNED_SHORT;
	public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV;
	public static final int GL_UNSIGNED_SHORT_4_4_4_4 = GL12.GL_UNSIGNED_SHORT_4_4_4_4;
	public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV;
	public static final int GL_UNSIGNED_SHORT_5_5_5_1 = GL12.GL_UNSIGNED_SHORT_5_5_5_1;
	public static final int GL_UNSIGNED_SHORT_5_6_5 = GL12.GL_UNSIGNED_SHORT_5_6_5;
	public static final int GL_UNSIGNED_SHORT_5_6_5_REV = GL12.GL_UNSIGNED_SHORT_5_6_5_REV;
	public static final int GL_UPPER_LEFT = GL20.GL_UPPER_LEFT;
	
	//XXX V
	
	public static final int GL_V2F = GL11.GL_V2F;
	public static final int GL_V3F = GL11.GL_V3F;
	public static final int GL_VALIDATE_STATUS = GL20.GL_VALIDATE_STATUS;
	public static final int GL_VENDOR = GL11.GL_VENDOR;
	public static final int GL_VERSION = GL11.GL_VERSION;
	public static final int GL_VERTEX_ARRAY = GL11.GL_VERTEX_ARRAY;
	public static final int GL_VERTEX_ARRAY_BINDING = GL30.GL_VERTEX_ARRAY_BINDING;
	public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = GL15.GL_VERTEX_ARRAY_BUFFER_BINDING;
	public static final int GL_VERTEX_ARRAY_POINTER = GL11.GL_VERTEX_ARRAY_POINTER;
	public static final int GL_VERTEX_ARRAY_SIZE = GL11.GL_VERTEX_ARRAY_SIZE;
	public static final int GL_VERTEX_ARRAY_STRIDE = GL11.GL_VERTEX_ARRAY_STRIDE;
	public static final int GL_VERTEX_ARRAY_TYPE = GL11.GL_VERTEX_ARRAY_TYPE;
	public static final int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = GL42.GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT;
	public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = GL15.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING;
	public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = GL33.GL_VERTEX_ATTRIB_ARRAY_DIVISOR;
	public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = GL20.GL_VERTEX_ATTRIB_ARRAY_ENABLED;
	public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = GL30.GL_VERTEX_ATTRIB_ARRAY_INTEGER;
	public static final int GL_VERTEX_ATTRIB_ARRAY_LONG = GL43.GL_VERTEX_ATTRIB_ARRAY_LONG;
	public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = GL20.GL_VERTEX_ATTRIB_ARRAY_NORMALIZED;
	public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = GL20.GL_VERTEX_ATTRIB_ARRAY_POINTER;
	public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = GL20.GL_VERTEX_ATTRIB_ARRAY_SIZE;
	public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = GL20.GL_VERTEX_ATTRIB_ARRAY_STRIDE;
	public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = GL20.GL_VERTEX_ATTRIB_ARRAY_TYPE;
	public static final int GL_VERTEX_ATTRIB_BINDING = GL43.GL_VERTEX_ATTRIB_BINDING;
	public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = GL43.GL_VERTEX_ATTRIB_RELATIVE_OFFSET;
	public static final int GL_VERTEX_BINDING_DIVISOR = GL43.GL_VERTEX_BINDING_DIVISOR;
	public static final int GL_VERTEX_BINDING_OFFSET = GL43.GL_VERTEX_BINDING_OFFSET;
	public static final int GL_VERTEX_BINDING_STRIDE = GL43.GL_VERTEX_BINDING_STRIDE;
	public static final int GL_VERTEX_PROGRAM_POINT_SIZE = GL20.GL_VERTEX_PROGRAM_POINT_SIZE;
	public static final int GL_VERTEX_PROGRAM_TWO_SIDE = GL20.GL_VERTEX_PROGRAM_TWO_SIDE;
	public static final int GL_VERTEX_SHADER = GL20.GL_VERTEX_SHADER;
	public static final int GL_VERTEX_SHADER_BIT = GL41.GL_VERTEX_SHADER_BIT;
	public static final int GL_VERTEX_SUBROUTINE = GL43.GL_VERTEX_SUBROUTINE;
	public static final int GL_VERTEX_SUBROUTINE_UNIFORM = GL43.GL_VERTEX_SUBROUTINE_UNIFORM;
	public static final int GL_VERTEX_TEXTURE = GL43.GL_VERTEX_TEXTURE;
	public static final int GL_VIEW_CLASS_128_BITS = GL43.GL_VIEW_CLASS_128_BITS;
	public static final int GL_VIEW_CLASS_16_BITS = GL43.GL_VIEW_CLASS_16_BITS;
	public static final int GL_VIEW_CLASS_24_BITS = GL43.GL_VIEW_CLASS_24_BITS;
	public static final int GL_VIEW_CLASS_32_BITS = GL43.GL_VIEW_CLASS_32_BITS;
	public static final int GL_VIEW_CLASS_48_BITS = GL43.GL_VIEW_CLASS_48_BITS;
	public static final int GL_VIEW_CLASS_64_BITS = GL43.GL_VIEW_CLASS_64_BITS;
	public static final int GL_VIEW_CLASS_8_BITS = GL43.GL_VIEW_CLASS_8_BITS;
	public static final int GL_VIEW_CLASS_96_BITS = GL43.GL_VIEW_CLASS_96_BITS;
	public static final int GL_VIEW_CLASS_BPTC_FLOAT = GL43.GL_VIEW_CLASS_BPTC_FLOAT;
	public static final int GL_VIEW_CLASS_BPTC_UNORM = GL43.GL_VIEW_CLASS_BPTC_UNORM;
	public static final int GL_VIEW_CLASS_RGTC1_RED = GL43.GL_VIEW_CLASS_RGTC1_RED;
	public static final int GL_VIEW_CLASS_RGTC2_RG = GL43.GL_VIEW_CLASS_RGTC2_RG;
	public static final int GL_VIEW_CLASS_S3TC_DXT1_RGB = GL43.GL_VIEW_CLASS_S3TC_DXT1_RGB;
	public static final int GL_VIEW_CLASS_S3TC_DXT1_RGBA = GL43.GL_VIEW_CLASS_S3TC_DXT1_RGBA;
	public static final int GL_VIEW_CLASS_S3TC_DXT3_RGBA = GL43.GL_VIEW_CLASS_S3TC_DXT3_RGBA;
	public static final int GL_VIEW_CLASS_S3TC_DXT5_RGBA = GL43.GL_VIEW_CLASS_S3TC_DXT5_RGBA;
	public static final int GL_VIEW_COMPATIBILITY_CLASS = GL43.GL_VIEW_COMPATIBILITY_CLASS;
	public static final int GL_VIEWPORT = GL41.GL_VIEWPORT;
	public static final int GL_VIEWPORT_BIT = GL11.GL_VIEWPORT_BIT;
	public static final int GL_VIEWPORT_BOUNDS_RANGE = GL41.GL_VIEWPORT_BOUNDS_RANGE;
	public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = GL41.GL_VIEWPORT_INDEX_PROVOKING_VERTEX;
	public static final int GL_VIEWPORT_SUBPIXEL_BITS = GL41.GL_VIEWPORT_SUBPIXEL_BITS;
	
	//XXX W
	
	public static final int GL_WAIT_FAILED = GL32.GL_WAIT_FAILED;
	public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = GL15.GL_WEIGHT_ARRAY_BUFFER_BINDING;
	public static final int GL_WRITE_ONLY = GL15.GL_WRITE_ONLY;
	
	//XXX X
	
	public static final int GL_XOR = GL11.GL_XOR;
	
	//XXX Z
	
	public static final int GL_ZERO = GL11.GL_ZERO;
	public static final int GL_ZOOM_X = GL11.GL_ZOOM_X;
	public static final int GL_ZOOM_Y = GL11.GL_ZOOM_Y;
	//This looks like a typo (given the surrounding elements)... so I'll add an alias.
	public static final int IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = GL42.IMAGE_FORMAT_COMPATIBILITY_BY_CLASS;
	public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = GL42.IMAGE_FORMAT_COMPATIBILITY_BY_CLASS;
	
	//XXX A
	
	public static void glActiveShaderProgram(int pipeline, int program)
	{
		GL41.glActiveShaderProgram(pipeline, program);
		
	}
	
	public static void glActiveTexture(int texture)
	{
		GL13.glActiveTexture(texture);
		
	}
	
	public static void glActiveTexture(ITexture texture)
	{
		glActiveTexture(texture == null ? 0 : texture.getTexture());
		
	}
	
	public static void glAttachShader(int program, int shader)
	{
		GL20.glAttachShader(program, shader);
		
	}
	
	//XXX B
	
	public static void glBeginConditionalRender(int id, int mode)
	{
		GL30.glBeginConditionalRender(id, mode);
		
	}
	
	public static void glBeginQuery(int target, int id)
	{
		GL15.glBeginQuery(target, id);
		
	}
	
	public static void glBeginQueryIndexed(int target, int index, int id)
	{
		GL40.glBeginQueryIndexed(target, index, id);
		
	}
	
	public static void glBeginTransformFeedback(int primitiveMode)
	{
		GL30.glBeginTransformFeedback(primitiveMode);
		
	}
	
	public static void glBindAttribLocation(int program, int index, ByteBuffer name)
	{
		GL20.glBindAttribLocation(program, index, name);
		
	}
	
	public static void glBindAttribLocation(int program, int index, CharSequence name)
	{
		GL20.glBindAttribLocation(program, index, name);
		
	}
	
	public static void glBindBuffer(int target, int buffer)
	{
		GL15.glBindBuffer(target, buffer);
		
	}
	
	public static void glBindBuffer(VertexBufferObject vbo)
	{
		glBindBuffer(vbo.t, vbo.id);
		
	}
	
	public static void glBindBufferBase(int target, int index, int buffer)
	{
		GL30.glBindBufferBase(target, index, buffer);
		
	}
	
	public static void glBindBufferRange(int target, int index, int buffer, long offset, long size)
	{
		GL30.glBindBufferRange(target, index, buffer, offset, size);
		
	}
	
	public static void glBindBuffersBase(int target, int first, int count, IntBuffer buffers)
	{
		GL44.glBindBuffersBase(target, first, count, buffers);
		
	}
	
	public static void glBindBuffersRange(int target, int first, int count, IntBuffer buffers, PointerBuffer offsets, PointerBuffer sizes)
	{
		GL44.glBindBuffersRange(target, first, count, buffers, offsets, sizes);
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, ByteBuffer name)
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
	}
	
	public static void glBindFragDataLocation(int program, int colorNumber, CharSequence name)
	{
		GL30.glBindFragDataLocation(program, colorNumber, name);
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, ByteBuffer name)
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
	}
	
	public static void glBindFragDataLocationIndexed(int program, int colorNumber, int index, CharSequence name)
	{
		GL33.glBindFragDataLocationIndexed(program, colorNumber, index, name);
		
	}
	
	public static void glBindFramebuffer(int target, int framebuffer)
	{
		GL30.glBindFramebuffer(target, framebuffer);
		
	}
	
	public static void glBindImageTexture(int unit, int texture, int level, boolean layered, int layer, int access, int format)
	{
		GL42.glBindImageTexture(unit, texture, level, layered, layer, access, format);
		
	}
	
	public static void glBindImageTextures(int first, int count, IntBuffer textures)
	{
		GL44.glBindImageTextures(first, count, textures);
		
	}
	
	public static void glBindProgramPipeline(int pipeline)
	{
		GL41.glBindProgramPipeline(pipeline);
		
	}
	
	public static void glBindRenderbuffer(int target, int renderbuffer)
	{
		GL30.glBindRenderbuffer(target, renderbuffer);
		
	}
	
	public static void glBindSampler(int unit, int sampler)
	{
		GL33.glBindSampler(unit, sampler);
		
	}
	
	public static void glBindSamplers(int first, int count, IntBuffer samplers)
	{
		GL44.glBindSamplers(first, count, samplers);
		
	}
	
	public static void glBindTexture(int target, int texture)
	{
		GL11.glBindTexture(target, texture);
		
	}
	
	public static void glBindTexture(int target, ITexture texture)
	{
		GL11.glBindTexture(target, texture == null ? 0 : texture.getTexture());
		
	}
	
	public static void glBindTextures(int first, int count, IntBuffer textures)
	{
		GL44.glBindTextures(first, count, textures);
		
	}
	
	public static void glBindTransformFeedback(int target, int id)
	{
		GL40.glBindTransformFeedback(target, id);
		
	}
	
	public static void glBindVertexArray(int array)
	{
		GL30.glBindVertexArray(array);
		
	}
	
	public static void glBindVertexBuffer(int bindingindex, int buffer, long offset, int stride)
	{
		GL43.glBindVertexBuffer(bindingindex, buffer, offset, stride);
		
	}
	
	public static void glBindVertexBuffers(int first, int count, IntBuffer buffers, PointerBuffer offsets, IntBuffer strides)
	{
		GL44.glBindVertexBuffers(first, count, buffers, offsets, strides);
		
	}
	
	public static void glBlendColor(float red, float green, float blue, float alpha)
	{
		GL14.glBlendColor(red, green, blue, alpha);
		
	}
	
	public static void glBlendEquation(int mode)
	{
		GL14.glBlendEquation(mode);
		
	}
	
	public static void glBlendEquationi(int buf, int mode)
	{
		GL40.glBlendEquationi(buf, mode);
		
	}
	
	public static void glBlendEquationSeparate(int modeRGB, int modeAlpha)
	{
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
	}
	
	public static void glBlendEquationSeparatei(int buf, int modeRGB, int modeAlpha)
	{
		GL40.glBlendEquationSeparatei(buf, modeRGB, modeAlpha);
		
	}
	
	public static void glBlendFunc(int sfactor, int dfactor)
	{
		GL11.glBlendFunc(sfactor, dfactor);
		
	}
	
	public static void glBlendFunci(int buf, int src, int dst)
	{
		GL40.glBlendFunci(buf, src, dst);
		
	}
	
	public static void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha)
	{
		GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
		
	}
	
	public static void glBlendFuncSeparatei(int buf, int srcRGB, int dstRGB, int srcAlpha, int dstAlpha)
	{
		GL40.glBlendFuncSeparatei(buf, srcRGB, dstRGB, srcAlpha, dstAlpha);
		
	}
	
	public static void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter)
	{
		GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		
	}
	
	public static void glBufferData(int target, ByteBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, DoubleBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, FloatBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, IntBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferData(int target, long data_size, int usage)
	{
		GL15.glBufferData(target, data_size, usage);
		
	}
	
	public static void glBufferData(int target, ShortBuffer data, int usage)
	{
		GL15.glBufferData(target, data, usage);
		
	}
	
	public static void glBufferStorage(int target, ByteBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, DoubleBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, FloatBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, IntBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, long size, int flags)
	{
		GL44.glBufferStorage(target, size, flags);
		
	}
	
	public static void glBufferStorage(int target, LongBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferStorage(int target, ShortBuffer data, int flags)
	{
		GL44.glBufferStorage(target, data, flags);
		
	}
	
	public static void glBufferSubData(int target, long offset, ByteBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, DoubleBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, FloatBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, IntBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	public static void glBufferSubData(int target, long offset, ShortBuffer data)
	{
		GL15.glBufferSubData(target, offset, data);
		
	}
	
	//XXX C
	
	public static int glCheckFramebufferStatus(int target)
	{
		return GL30.glCheckFramebufferStatus(target);
	}
	
	public static void glClampColor(int target, int clamp)
	{
		GL30.glClampColor(target, clamp);
		
	}
	
	public static void glClear(int mask)
	{
		GL11.glClear(mask);
		
	}
	
	public static void glClearBuffer(int buffer, int drawbuffer, FloatBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	public static void glClearBuffer(int buffer, int drawbuffer, IntBuffer value)
	{
		GL30.glClearBuffer(buffer, drawbuffer, value);
		
	}
	
	public static void glClearBufferData(int target, int internalformat, int format, int type, ByteBuffer data)
	{
		GL43.glClearBufferData(target, internalformat, format, type, data);
		
	}
	
	public static void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil)
	{
		GL30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
		
	}
	
	public static void glClearBufferSubData(int target, int internalformat, long offset, int format, int type, ByteBuffer data)
	{
		GL43.glClearBufferSubData(target, internalformat, offset, format, type, data);
		
	}
	
	public static void glClearBufferu(int buffer, int drawbuffer, IntBuffer value)
	{
		GL30.glClearBufferu(buffer, drawbuffer, value);
		
	}
	
	public static void glClearColor(float red, float green, float blue, float alpha)
	{
		GL11.glClearColor(red, green, blue, alpha);
		
	}
	
	public static void glClearDepth(double depth)
	{
		GL11.glClearDepth(depth);
		
	}
	
	public static void glClearDepthf(float d)
	{
		GL41.glClearDepthf(d);
		
	}
	
	public static void glClearStencil(int s)
	{
		GL11.glClearStencil(s);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, ByteBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, DoubleBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, FloatBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, IntBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, LongBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexImage(int texture, int level, int format, int type, ShortBuffer data)
	{
		GL44.glClearTexImage(texture, level, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, LongBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer data)
	{
		GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		
	}
	
	public static int glClientWaitSync(GLSync sync, int flags, long timeout)
	{
		return GL32.glClientWaitSync(sync, flags, timeout);
	}
	
	public static void glColorMask(boolean red, boolean green, boolean blue, boolean alpha)
	{
		GL11.glColorMask(red, green, blue, alpha);
		
	}
	
	public static void glColorMaski(int buf, boolean r, boolean g, boolean b, boolean a)
	{
		GL30.glColorMaski(buf, r, g, b, a);
		
	}
	
	public static void glCompileShader(int shader)
	{
		GL20.glCompileShader(shader);
		
	}
	
	public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage1D(target, level, internalformat, width, border, data);
		
	}
	
	public static void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage1D(target, level, internalformat, width, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data);
		
	}
	
	public static void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, ByteBuffer data)
	{
		GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, data);
		
	}
	
	public static void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, data);
		
	}
	
	public static void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage1D(target, level, xoffset, width, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data);
		
	}
	
	public static void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, ByteBuffer data)
	{
		GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data);
		
	}
	
	public static void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int data_imageSize, long data_buffer_offset)
	{
		GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data_imageSize, data_buffer_offset);
		
	}
	
	public static void glCopyBufferSubData(int readtarget, int writetarget, long readoffset, long writeoffset, long size)
	{
		GL31.glCopyBufferSubData(readtarget, writetarget, readoffset, writeoffset, size);
		
	}
	
	public static void glCopyImageSubData(int srcName, int srcTarget, int srcLevel, int srcX, int srcY, int srcZ, int dstName, int dstTarget, int dstLevel, int dstX, int dstY, int dstZ, int srcWidth, int srcHeight, int srcDepth)
	{
		GL43.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
		
	}
	
	public static void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border)
	{
		GL11.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
		
	}
	
	public static void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border)
	{
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
		
	}
	
	public static void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width)
	{
		GL11.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
		
	}
	
	public static void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height)
	{
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
	}
	
	public static void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height)
	{
		GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
	}
	
	public static int glCreateProgram()
	{
		return GL20.glCreateProgram();
	}
	
	public static int glCreateShader(int type)
	{
		return GL20.glCreateShader(type);
	}
	
	public static int glCreateShaderProgram(int type, ByteBuffer string)
	{
		return GL41.glCreateShaderProgram(type, string);
	}
	
	public static int glCreateShaderProgram(int type, ByteBuffer[] strings)
	{
		return GL41.glCreateShaderProgram(type, strings);
	}
	
	public static int glCreateShaderProgram(int type, CharSequence string)
	{
		return GL41.glCreateShaderProgram(type, string);
	}
	
	public static int glCreateShaderProgram(int type, CharSequence[] strings)
	{
		return GL41.glCreateShaderProgram(type, strings);
	}
	
	public static int glCreateShaderProgram(int type, int count, ByteBuffer strings)
	{
		return GL41.glCreateShaderProgram(type, count, strings);
	}
	
	public static void glCullFace(int mode)
	{
		GL11.glCullFace(mode);
		
	}
	
	//XXX D
	
	public static void glDebugMessageCallback(KHRDebugCallback callback)
	{
		GL43.glDebugMessageCallback(callback);
		
	}
	
	public static void glDebugMessageControl(int source, int type, int severity, IntBuffer ids, boolean enabled)
	{
		GL43.glDebugMessageControl(source, type, severity, ids, enabled);
		
	}
	
	public static void glDebugMessageInsert(int source, int type, int id, int severity, ByteBuffer buf)
	{
		GL43.glDebugMessageInsert(source, type, id, severity, buf);
		
	}
	
	public static void glDebugMessageInsert(int source, int type, int id, int severity, CharSequence buf)
	{
		GL43.glDebugMessageInsert(source, type, id, severity, buf);
		
	}
	
	public static void glDeleteBuffers(int buffer)
	{
		GL15.glDeleteBuffers(buffer);
		
	}
	
	public static void glDeleteBuffers(IntBuffer buffers)
	{
		GL15.glDeleteBuffers(buffers);
		
	}
	
	public static void glDeleteBuffers(VertexBufferObject buffer)
	{
		glDeleteBuffers(buffer.id);
		
	}
	
	public static void glDeleteFramebuffers(int framebuffer)
	{
		GL30.glDeleteFramebuffers(framebuffer);
		
	}
	
	public static void glDeleteFramebuffers(IntBuffer framebuffers)
	{
		GL30.glDeleteFramebuffers(framebuffers);
		
	}
	
	public static void glDeleteProgram(int program)
	{
		GL20.glDeleteProgram(program);
		
	}
	
	public static void glDeleteProgram(GLProgram program)
	{
		glDeleteProgram(program.getId());
		
	}
	
	public static void glDeleteProgramPipelines(int pipeline)
	{
		GL41.glDeleteProgramPipelines(pipeline);
		
	}
	
	public static void glDeleteProgramPipelines(IntBuffer pipelines)
	{
		GL41.glDeleteProgramPipelines(pipelines);
		
	}
	
	public static void glDeleteQueries(int id)
	{
		GL15.glDeleteQueries(id);
		
	}
	
	public static void glDeleteQueries(IntBuffer ids)
	{
		GL15.glDeleteQueries(ids);
		
	}
	
	public static void glDeleteRenderbuffers(int renderbuffer)
	{
		GL30.glDeleteRenderbuffers(renderbuffer);
		
	}
	
	public static void glDeleteRenderbuffers(IntBuffer renderbuffers)
	{
		GL30.glDeleteRenderbuffers(renderbuffers);
		
	}
	
	public static void glDeleteSamplers(int sampler)
	{
		GL33.glDeleteSamplers(sampler);
		
	}
	
	public static void glDeleteSamplers(IntBuffer samplers)
	{
		GL33.glDeleteSamplers(samplers);
		
	}
	
	public static void glDeleteShader(int shader)
	{
		GL20.glDeleteShader(shader);
		
	}
	
	public static void glDeleteSync(GLSync sync)
	{
		GL32.glDeleteSync(sync);
		
	}
	
	public static void glDeleteTextures(int texture)
	{
		GL11.glDeleteTextures(texture);
		
	}
	
	public static void glDeleteTextures(IntBuffer textures)
	{
		GL11.glDeleteTextures(textures);
		
	}
	
	public static void glDeleteTransformFeedbacks(int id)
	{
		GL40.glDeleteTransformFeedbacks(id);
		
	}
	
	public static void glDeleteTransformFeedbacks(IntBuffer ids)
	{
		GL40.glDeleteTransformFeedbacks(ids);
		
	}
	
	public static void glDeleteVertexArrays(int array)
	{
		GL30.glDeleteVertexArrays(array);
		
	}
	
	public static void glDeleteVertexArrays(IntBuffer arrays)
	{
		GL30.glDeleteVertexArrays(arrays);
		
	}
	
	public static void glDepthFunc(int func)
	{
		GL11.glDepthFunc(func);
		
	}
	
	public static void glDepthMask(boolean flag)
	{
		GL11.glDepthMask(flag);
		
	}
	
	public static void glDepthRange(double zNear, double zFar)
	{
		GL11.glDepthRange(zNear, zFar);
		
	}
	
	public static void glDepthRangeArray(int first, DoubleBuffer v)
	{
		GL41.glDepthRangeArray(first, v);
		
	}
	
	public static void glDepthRangef(float n, float f)
	{
		GL41.glDepthRangef(n, f);
		
	}
	
	public static void glDepthRangeIndexed(int index, double n, double f)
	{
		GL41.glDepthRangeIndexed(index, n, f);
		
	}
	
	public static void glDetachShader(int program, int shader)
	{
		GL20.glDetachShader(program, shader);
		
	}
	
	public static void glDisable(int cap)
	{
		GL11.glDisable(cap);
		
	}
	
	public static void glDisablei(int target, int index)
	{
		GL30.glDisablei(target, index);
		
	}
	
	public static void glDisableVertexAttribArray(int index)
	{
		GL20.glDisableVertexAttribArray(index);
		
	}
	
	public static void glDispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z)
	{
		GL43.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
		
	}
	
	public static void glDispatchComputeIndirect(long indirect)
	{
		GL43.glDispatchComputeIndirect(indirect);
		
	}
	
	public static void glDrawArrays(int mode, int first, int count)
	{
		GL11.glDrawArrays(mode, first, count);
		
	}
	
	public static void glDrawArraysIndirect(int mode, ByteBuffer indirect)
	{
		GL40.glDrawArraysIndirect(mode, indirect);
		
	}
	
	public static void glDrawArraysIndirect(int mode, IntBuffer indirect)
	{
		GL40.glDrawArraysIndirect(mode, indirect);
		
	}
	
	public static void glDrawArraysIndirect(int mode, long indirect_buffer_offset)
	{
		GL40.glDrawArraysIndirect(mode, indirect_buffer_offset);
		
	}
	
	public static void glDrawArraysInstanced(int mode, int first, int count, int primcount)
	{
		GL31.glDrawArraysInstanced(mode, first, count, primcount);
		
	}
	
	public static void glDrawArraysInstancedBaseInstance(int mode, int first, int count, int primcount, int baseinstance)
	{
		GL42.glDrawArraysInstancedBaseInstance(mode, first, count, primcount, baseinstance);
		
	}
	
	public static void glDrawBuffer(int mode)
	{
		GL11.glDrawBuffer(mode);
		
	}
	
	public static void glDrawBuffers(int buffer)
	{
		GL20.glDrawBuffers(buffer);
		
	}
	
	public static void glDrawBuffers(IntBuffer buffers)
	{
		GL20.glDrawBuffers(buffers);
		
	}
	
	public static void glDrawElements(int mode, ByteBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElements(int mode, int count, int type, ByteBuffer indices)
	{
		GL11.glDrawElements(mode, count, type, indices);
		
	}
	
	public static void glDrawElements(int mode, int indices_count, int type, long indices_buffer_offset)
	{
		GL11.glDrawElements(mode, indices_count, type, indices_buffer_offset);
		
	}
	
	public static void glDrawElements(int mode, IntBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElements(int mode, ShortBuffer indices)
	{
		GL11.glDrawElements(mode, indices);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, ByteBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, IntBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsBaseVertex(int mode, ShortBuffer indices, int basevertex)
	{
		GL32.glDrawElementsBaseVertex(mode, indices, basevertex);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, ByteBuffer indirect)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, IntBuffer indirect)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect);
		
	}
	
	public static void glDrawElementsIndirect(int mode, int type, long indirect_buffer_offset)
	{
		GL40.glDrawElementsIndirect(mode, type, indirect_buffer_offset);
		
	}
	
	public static void glDrawElementsInstanced(int mode, ByteBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, int indices_count, int type, long indices_buffer_offset, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices_count, type, indices_buffer_offset, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, IntBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstanced(int mode, ShortBuffer indices, int primcount)
	{
		GL31.glDrawElementsInstanced(mode, indices, primcount);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, ByteBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices_count, type, indices_buffer_offset, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, IntBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseInstance(int mode, ShortBuffer indices, int primcount, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, ByteBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices_count, type, indices_buffer_offset, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, IntBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertex(int mode, ShortBuffer indices, int primcount, int basevertex)
	{
		GL32.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ByteBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, int indices_count, int type, long indices_buffer_offset, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices_count, type, indices_buffer_offset, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, IntBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawElementsInstancedBaseVertexBaseInstance(int mode, ShortBuffer indices, int primcount, int basevertex, int baseinstance)
	{
		GL42.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, ByteBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset)
	{
		GL12.glDrawRangeElements(mode, start, end, indices_count, type, indices_buffer_offset);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, IntBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElements(int mode, int start, int end, ShortBuffer indices)
	{
		GL12.glDrawRangeElements(mode, start, end, indices);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ByteBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, int indices_count, int type, long indices_buffer_offset, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices_count, type, indices_buffer_offset, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, IntBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawRangeElementsBaseVertex(int mode, int start, int end, ShortBuffer indices, int basevertex)
	{
		GL32.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
		
	}
	
	public static void glDrawTransformFeedback(int mode, int id)
	{
		GL40.glDrawTransformFeedback(mode, id);
		
	}
	
	public static void glDrawTransformFeedbackInstanced(int mode, int id, int primcount)
	{
		GL42.glDrawTransformFeedbackInstanced(mode, id, primcount);
		
	}
	
	public static void glDrawTransformFeedbackStream(int mode, int id, int stream)
	{
		GL40.glDrawTransformFeedbackStream(mode, id, stream);
		
	}
	
	public static void glDrawTransformFeedbackStreamInstanced(int mode, int id, int stream, int primcount)
	{
		GL42.glDrawTransformFeedbackStreamInstanced(mode, id, stream, primcount);
		
	}
	
	//XXX E
	
	public static void glEnable(int cap)
	{
		GL11.glEnable(cap);
		
	}
	
	public static void glEnablei(int target, int index)
	{
		GL30.glEnablei(target, index);
		
	}
	
	public static void glEnableVertexAttribArray(int index)
	{
		GL20.glEnableVertexAttribArray(index);
		
	}
	
	public static void glEndConditionalRender()
	{
		GL30.glEndConditionalRender();
		
	}
	
	public static void glEndQuery(int target)
	{
		GL15.glEndQuery(target);
		
	}
	
	public static void glEndQueryIndexed(int target, int index)
	{
		GL40.glEndQueryIndexed(target, index);
		
	}
	
	public static void glEndTransformFeedback()
	{
		GL30.glEndTransformFeedback();
		
	}
	
	//XXX F
	
	public static GLSync glFenceSync(int condition, int flags)
	{
		return GL32.glFenceSync(condition, flags);
	}
	
	public static void glFinish()
	{
		GL11.glFinish();
		
	}
	
	public static void glFlush()
	{
		GL11.glFlush();
		
	}
	
	public static void glFlushMappedBufferRange(int target, long offset, long length)
	{
		GL30.glFlushMappedBufferRange(target, offset, length);
		
	}
	
	public static void glFramebufferParameteri(int target, int pname, int param)
	{
		GL43.glFramebufferParameteri(target, pname, param);
		
	}
	
	public static void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer)
	{
		GL30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
		
	}
	
	public static void glFramebufferTexture(int target, int attachment, int texture, int level)
	{
		GL32.glFramebufferTexture(target, attachment, texture, level);
		
	}
	
	public static void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level)
	{
		GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		
	}
	
	public static void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset)
	{
		GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		
	}
	
	public static void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer)
	{
		GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		
	}
	
	public static void glFrontFace(int mode)
	{
		GL11.glFrontFace(mode);
		
	}
	
	//XXX G
	
	public static int glGenBuffers()
	{
		return GL15.glGenBuffers();
	}
	
	public static void glGenBuffers(IntBuffer buffers)
	{
		GL15.glGenBuffers(buffers);
		
	}
	
	public static void glGenerateMipmap(int target)
	{
		GL30.glGenerateMipmap(target);
		
	}
	
	public static int glGenFramebuffers()
	{
		return GL30.glGenFramebuffers();
	}
	
	public static void glGenFramebuffers(IntBuffer framebuffers)
	{
		GL30.glGenFramebuffers(framebuffers);
		
	}
	
	public static int glGenProgramPipelines()
	{
		return GL41.glGenProgramPipelines();
	}
	
	public static void glGenProgramPipelines(IntBuffer pipelines)
	{
		GL41.glGenProgramPipelines(pipelines);
		
	}
	
	public static int glGenQueries()
	{
		return GL15.glGenQueries();
	}
	
	public static void glGenQueries(IntBuffer ids)
	{
		GL15.glGenQueries(ids);
		
	}
	
	public static int glGenRenderbuffers()
	{
		return GL30.glGenRenderbuffers();
	}
	
	public static void glGenRenderbuffers(IntBuffer renderbuffers)
	{
		GL30.glGenRenderbuffers(renderbuffers);
		
	}
	
	public static int glGenSamplers()
	{
		return GL33.glGenSamplers();
	}
	
	public static void glGenSamplers(IntBuffer samplers)
	{
		GL33.glGenSamplers(samplers);
		
	}
	
	public static int glGenTextures()
	{
		return GL11.glGenTextures();
	}
	
	public static void glGenTextures(IntBuffer textures)
	{
		GL11.glGenTextures(textures);
		
	}
	
	public static int glGenTransformFeedbacks()
	{
		return GL40.glGenTransformFeedbacks();
	}
	
	public static void glGenTransformFeedbacks(IntBuffer ids)
	{
		GL40.glGenTransformFeedbacks(ids);
		
	}
	
	public static int glGenVertexArrays()
	{
		return GL30.glGenVertexArrays();
	}
	
	public static void glGenVertexArrays(IntBuffer arrays)
	{
		GL30.glGenVertexArrays(arrays);
		
	}
	
	public static int glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname)
	{
		return GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname);
	}
	
	public static void glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname, IntBuffer params)
	{
		GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname, params);
		
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength);
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength, sizeType);
	}
	
	public static void glGetActiveAttrib(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL20.glGetActiveAttrib(program, index, length, size, type, name);
		
	}
	
	public static int glGetActiveAttribSize(int program, int index)
	{
		return GL20.glGetActiveAttribSize(program, index);
	}
	
	public static int glGetActiveAttribType(int program, int index)
	{
		return GL20.glGetActiveAttribType(program, index);
	}
	
	public static String glGetActiveSubroutineName(int program, int shadertype, int index, int bufsize)
	{
		return GL40.glGetActiveSubroutineName(program, shadertype, index, bufsize);
	}
	
	public static void glGetActiveSubroutineName(int program, int shadertype, int index, IntBuffer length, ByteBuffer name)
	{
		GL40.glGetActiveSubroutineName(program, shadertype, index, length, name);
		
	}
	
	public static void glGetActiveSubroutineUniform(int program, int shadertype, int index, int pname, IntBuffer values)
	{
		GL40.glGetActiveSubroutineUniform(program, shadertype, index, pname, values);
		
	}
	
	public static int glGetActiveSubroutineUniformi(int program, int shadertype, int index, int pname)
	{
		return GL40.glGetActiveSubroutineUniformi(program, shadertype, index, pname);
	}
	
	public static String glGetActiveSubroutineUniformName(int program, int shadertype, int index, int bufsize)
	{
		return GL40.glGetActiveSubroutineUniformName(program, shadertype, index, bufsize);
	}
	
	public static void glGetActiveSubroutineUniformName(int program, int shadertype, int index, IntBuffer length, ByteBuffer name)
	{
		GL40.glGetActiveSubroutineUniformName(program, shadertype, index, length, name);
		
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength)
	{
		return GL20.glGetActiveUniform(program, index, maxLength);
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveUniform(program, index, maxLength, sizeType);
	}
	
	public static void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL20.glGetActiveUniform(program, index, length, size, type, name);
		
	}
	
	public static void glGetActiveUniformBlock(int program, int uniformBlockIndex, int pname, IntBuffer params)
	{
		GL31.glGetActiveUniformBlock(program, uniformBlockIndex, pname, params);
		
	}
	
	public static int glGetActiveUniformBlocki(int program, int uniformBlockIndex, int pname)
	{
		return GL31.glGetActiveUniformBlocki(program, uniformBlockIndex, pname);
	}
	
	public static String glGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize)
	{
		return GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
	}
	
	public static void glGetActiveUniformBlockName(int program, int uniformBlockIndex, IntBuffer length, ByteBuffer uniformBlockName)
	{
		GL31.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
		
	}
	
	public static String glGetActiveUniformName(int program, int uniformIndex, int bufSize)
	{
		return GL31.glGetActiveUniformName(program, uniformIndex, bufSize);
	}
	
	public static void glGetActiveUniformName(int program, int uniformIndex, IntBuffer length, ByteBuffer uniformName)
	{
		GL31.glGetActiveUniformName(program, uniformIndex, length, uniformName);
		
	}
	
	public static void glGetActiveUniforms(int program, IntBuffer uniformIndices, int pname, IntBuffer params)
	{
		GL31.glGetActiveUniforms(program, uniformIndices, pname, params);
		
	}
	
	public static int glGetActiveUniformsi(int program, int uniformIndex, int pname)
	{
		return GL31.glGetActiveUniformsi(program, uniformIndex, pname);
	}
	
	public static int glGetActiveUniformType(int program, int index)
	{
		return GL20.glGetActiveUniformType(program, index);
	}
	
	public static void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders)
	{
		GL20.glGetAttachedShaders(program, count, shaders);
		
	}
	
	public static int glGetAttribLocation(int program, ByteBuffer name)
	{
		return GL20.glGetAttribLocation(program, name);
	}
	
	public static int glGetAttribLocation(int program, CharSequence name)
	{
		return GL20.glGetAttribLocation(program, name);
	}
	
	public static boolean glGetBoolean(int pname)
	{
		return GL11.glGetBoolean(pname);
	}
	
	public static void glGetBoolean(int pname, ByteBuffer params)
	{
		GL11.glGetBoolean(pname, params);
		
	}
	
	public static boolean glGetBoolean(int value, int index)
	{
		return GL30.glGetBoolean(value, index);
	}
	
	public static void glGetBoolean(int value, int index, ByteBuffer data)
	{
		GL30.glGetBoolean(value, index, data);
		
	}
	
	public static void glGetBufferParameter(int target, int pname, IntBuffer params)
	{
		GL15.glGetBufferParameter(target, pname, params);
		
	}
	
	public static void glGetBufferParameter(int target, int pname, LongBuffer params)
	{
		GL32.glGetBufferParameter(target, pname, params);
		
	}
	
	public static int glGetBufferParameteri(int target, int pname)
	{
		return GL15.glGetBufferParameteri(target, pname);
	}
	
	public static long glGetBufferParameteri64(int target, int pname)
	{
		return GL32.glGetBufferParameteri64(target, pname);
	}
	
	public static ByteBuffer glGetBufferPointer(int target, int pname)
	{
		return GL15.glGetBufferPointer(target, pname);
	}
	
	public static void glGetBufferSubData(int target, long offset, ByteBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, DoubleBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, FloatBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, IntBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetBufferSubData(int target, long offset, ShortBuffer data)
	{
		GL15.glGetBufferSubData(target, offset, data);
		
	}
	
	public static void glGetClipPlane(int plane, DoubleBuffer equation)
	{
		GL11.glGetClipPlane(plane, equation);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, ByteBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, IntBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, long img_buffer_offset)
	{
		GL13.glGetCompressedTexImage(target, lod, img_buffer_offset);
		
	}
	
	public static void glGetCompressedTexImage(int target, int lod, ShortBuffer img)
	{
		GL13.glGetCompressedTexImage(target, lod, img);
		
	}
	
	public static int glGetDebugMessageLog(int count, IntBuffer sources, IntBuffer types, IntBuffer ids, IntBuffer severities, IntBuffer lengths, ByteBuffer messageLog)
	{
		return GL43.glGetDebugMessageLog(count, sources, types, ids, severities, lengths, messageLog);
	}
	
	public static double glGetDouble(int pname)
	{
		return GL11.glGetDouble(pname);
	}
	
	public static void glGetDouble(int pname, DoubleBuffer params)
	{
		GL11.glGetDouble(pname, params);
		
	}
	
	public static double glGetDouble(int target, int index)
	{
		return GL41.glGetDouble(target, index);
	}
	
	public static void glGetDouble(int target, int index, DoubleBuffer data)
	{
		GL41.glGetDouble(target, index, data);
		
	}
	
	public static int glGetError()
	{
		return GL11.glGetError();
	}
	
	public static float glGetFloat(int pname)
	{
		return GL11.glGetFloat(pname);
	}
	
	public static void glGetFloat(int pname, FloatBuffer params)
	{
		GL11.glGetFloat(pname, params);
		
	}
	
	public static float glGetFloat(int target, int index)
	{
		return GL41.glGetFloat(target, index);
	}
	
	public static void glGetFloat(int target, int index, FloatBuffer data)
	{
		GL41.glGetFloat(target, index, data);
		
	}
	
	public static int glGetFragDataIndex(int program, ByteBuffer name)
	{
		return GL33.glGetFragDataIndex(program, name);
	}
	
	public static int glGetFragDataIndex(int program, CharSequence name)
	{
		return GL33.glGetFragDataIndex(program, name);
	}
	
	public static int glGetFragDataLocation(int program, ByteBuffer name)
	{
		return GL30.glGetFragDataLocation(program, name);
	}
	
	public static int glGetFragDataLocation(int program, CharSequence name)
	{
		return GL30.glGetFragDataLocation(program, name);
	}
	
	public static void glGetFramebufferAttachmentParameter(int target, int attachment, int pname, IntBuffer params)
	{
		GL30.glGetFramebufferAttachmentParameter(target, attachment, pname, params);
		
	}
	
	public static int glGetFramebufferAttachmentParameteri(int target, int attachment, int pname)
	{
		return GL30.glGetFramebufferAttachmentParameteri(target, attachment, pname);
	}
	
	public static void glGetFramebufferParameter(int target, int pname, IntBuffer params)
	{
		GL43.glGetFramebufferParameter(target, pname, params);
		
	}
	
	public static int glGetFramebufferParameteri(int target, int pname)
	{
		return GL43.glGetFramebufferParameteri(target, pname);
	}
	
	public static int glGetInteger(int pname)
	{
		return GL11.glGetInteger(pname);
	}
	
	public static int glGetInteger(int value, int index)
	{
		return GL30.glGetInteger(value, index);
	}
	
	public static void glGetInteger(int value, int index, IntBuffer data)
	{
		GL30.glGetInteger(value, index, data);
		
	}
	
	public static void glGetInteger(int pname, IntBuffer params)
	{
		GL11.glGetInteger(pname, params);
		
	}
	
	public static long glGetInteger64(int pname)
	{
		return GL32.glGetInteger64(pname);
	}
	
	public static long glGetInteger64(int value, int index)
	{
		return GL32.glGetInteger64(value, index);
	}
	
	public static void glGetInteger64(int value, int index, LongBuffer data)
	{
		GL32.glGetInteger64(value, index, data);
		
	}
	
	public static void glGetInteger64(int pname, LongBuffer data)
	{
		GL32.glGetInteger64(pname, data);
		
	}
	
	public static int glGetInternalformat(int target, int internalformat, int pname)
	{
		return GL42.glGetInternalformat(target, internalformat, pname);
	}
	
	public static void glGetInternalformat(int target, int internalformat, int pname, IntBuffer params)
	{
		GL42.glGetInternalformat(target, internalformat, pname, params);
		
	}
	
	public static void glGetInternalformat(int target, int internalformat, int pname, LongBuffer params)
	{
		GL43.glGetInternalformat(target, internalformat, pname, params);
		
	}
	
	public static long glGetInternalformati64(int target, int internalformat, int pname)
	{
		return GL43.glGetInternalformati64(target, internalformat, pname);
	}
	
	public static void glGetMultisample(int pname, int index, FloatBuffer val)
	{
		GL32.glGetMultisample(pname, index, val);
		
	}
	
	public static String glGetObjectLabel(int identifier, int name, int bufSize)
	{
		return GL43.glGetObjectLabel(identifier, name, bufSize);
	}
	
	public static void glGetObjectLabel(int identifier, int name, IntBuffer length, ByteBuffer label)
	{
		GL43.glGetObjectLabel(identifier, name, length, label);
		
	}
	
	public static String glGetObjectPtrLabel(PointerWrapper ptr, int bufSize)
	{
		return GL43.glGetObjectPtrLabel(ptr, bufSize);
	}
	
	public static void glGetObjectPtrLabel(PointerWrapper ptr, IntBuffer length, ByteBuffer label)
	{
		GL43.glGetObjectPtrLabel(ptr, length, label);
		
	}
	
	public static void glGetProgram(int program, int pname, IntBuffer params)
	{
		GL20.glGetProgram(program, pname, params);
		
	}
	
	public static void glGetProgramBinary(int program, IntBuffer length, IntBuffer binaryFormat, ByteBuffer binary)
	{
		GL41.glGetProgramBinary(program, length, binaryFormat, binary);
		
	}
	
	public static int glGetProgrami(int program, int pname)
	{
		return GL20.glGetProgrami(program, pname);
	}
	
	public static String glGetProgramInfoLog(int program, int maxLength)
	{
		return GL20.glGetProgramInfoLog(program, maxLength);
	}
	
	public static void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog)
	{
		GL20.glGetProgramInfoLog(program, length, infoLog);
		
	}
	
	public static void glGetProgramInterface(int program, int programInterface, int pname, IntBuffer params)
	{
		GL43.glGetProgramInterface(program, programInterface, pname, params);
		
	}
	
	public static int glGetProgramInterfacei(int program, int programInterface, int pname)
	{
		return GL43.glGetProgramInterfacei(program, programInterface, pname);
	}
	
	public static void glGetProgramPipeline(int pipeline, int pname, IntBuffer params)
	{
		GL41.glGetProgramPipeline(pipeline, pname, params);
		
	}
	
	public static int glGetProgramPipelinei(int pipeline, int pname)
	{
		return GL41.glGetProgramPipelinei(pipeline, pname);
	}
	
	public static String glGetProgramPipelineInfoLog(int pipeline, int bufSize)
	{
		return GL41.glGetProgramPipelineInfoLog(pipeline, bufSize);
	}
	
	public static void glGetProgramPipelineInfoLog(int pipeline, IntBuffer length, ByteBuffer infoLog)
	{
		GL41.glGetProgramPipelineInfoLog(pipeline, length, infoLog);
		
	}
	
	public static void glGetProgramResource(int program, int programInterface, int index, IntBuffer props, IntBuffer length, IntBuffer params)
	{
		GL43.glGetProgramResource(program, programInterface, index, props, length, params);
		
	}
	
	public static int glGetProgramResourceIndex(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceIndex(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocation(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceLocation(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocation(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceLocation(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocationIndex(int program, int programInterface, ByteBuffer name)
	{
		return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
	}
	
	public static int glGetProgramResourceLocationIndex(int program, int programInterface, CharSequence name)
	{
		return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
	}
	
	public static String glGetProgramResourceName(int program, int programInterface, int index, int bufSize)
	{
		return GL43.glGetProgramResourceName(program, programInterface, index, bufSize);
	}
	
	public static void glGetProgramResourceName(int program, int programInterface, int index, IntBuffer length, ByteBuffer name)
	{
		GL43.glGetProgramResourceName(program, programInterface, index, length, name);
		
	}
	
	public static void glGetProgramStage(int program, int shadertype, int pname, IntBuffer values)
	{
		GL40.glGetProgramStage(program, shadertype, pname, values);
		
	}
	
	public static int glGetProgramStagei(int program, int shadertype, int pname)
	{
		return GL40.glGetProgramStagei(program, shadertype, pname);
	}
	
	public static void glGetQuery(int target, int pname, IntBuffer params)
	{
		GL15.glGetQuery(target, pname, params);
		
	}
	
	public static int glGetQueryi(int target, int pname)
	{
		return GL15.glGetQueryi(target, pname);
	}
	
	public static void glGetQueryIndexed(int target, int index, int pname, IntBuffer params)
	{
		GL40.glGetQueryIndexed(target, index, pname, params);
		
	}
	
	public static int glGetQueryIndexedi(int target, int index, int pname)
	{
		return GL40.glGetQueryIndexedi(target, index, pname);
	}
	
	public static void glGetQueryObject(int id, int pname, IntBuffer params)
	{
		GL15.glGetQueryObject(id, pname, params);
		
	}
	
	public static void glGetQueryObject(int id, int pname, LongBuffer params)
	{
		GL33.glGetQueryObject(id, pname, params);
		
	}
	
	public static int glGetQueryObjecti(int id, int pname)
	{
		return GL15.glGetQueryObjecti(id, pname);
	}
	
	public static long glGetQueryObjecti64(int id, int pname)
	{
		return GL33.glGetQueryObjecti64(id, pname);
	}
	
	public static void glGetQueryObjectu(int id, int pname, IntBuffer params)
	{
		GL15.glGetQueryObjectu(id, pname, params);
		
	}
	
	public static void glGetQueryObjectu(int id, int pname, LongBuffer params)
	{
		GL33.glGetQueryObjectu(id, pname, params);
		
	}
	
	public static int glGetQueryObjectui(int id, int pname)
	{
		return GL15.glGetQueryObjectui(id, pname);
	}
	
	public static long glGetQueryObjectui64(int id, int pname)
	{
		return GL33.glGetQueryObjectui64(id, pname);
	}
	
	public static void glGetRenderbufferParameter(int target, int pname, IntBuffer params)
	{
		GL30.glGetRenderbufferParameter(target, pname, params);
		
	}
	
	public static int glGetRenderbufferParameteri(int target, int pname)
	{
		return GL30.glGetRenderbufferParameteri(target, pname);
	}
	
	public static void glGetSamplerParameter(int sampler, int pname, FloatBuffer params)
	{
		GL33.glGetSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glGetSamplerParameter(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameter(sampler, pname, params);
		
	}
	
	public static float glGetSamplerParameterf(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterf(sampler, pname);
	}
	
	public static int glGetSamplerParameteri(int sampler, int pname)
	{
		return GL33.glGetSamplerParameteri(sampler, pname);
	}
	
	public static void glGetSamplerParameterI(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameterI(sampler, pname, params);
		
	}
	
	public static int glGetSamplerParameterIi(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIi(sampler, pname);
	}
	
	public static void glGetSamplerParameterIu(int sampler, int pname, IntBuffer params)
	{
		GL33.glGetSamplerParameterIu(sampler, pname, params);
		
	}
	
	public static int glGetSamplerParameterIui(int sampler, int pname)
	{
		return GL33.glGetSamplerParameterIui(sampler, pname);
	}
	
	public static void glGetShader(int shader, int pname, IntBuffer params)
	{
		GL20.glGetShader(shader, pname, params);
		
	}
	
	public static int glGetShaderi(int shader, int pname)
	{
		return GL20.glGetShaderi(shader, pname);
	}
	
	public static String glGetShaderInfoLog(int shader, int maxLength)
	{
		return GL20.glGetShaderInfoLog(shader, maxLength);
	}
	
	public static void glGetShaderInfoLog(int shader, IntBuffer length, ByteBuffer infoLog)
	{
		GL20.glGetShaderInfoLog(shader, length, infoLog);
		
	}
	
	public static void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision)
	{
		GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
		
	}
	
	public static String glGetShaderSource(int shader, int maxLength)
	{
		return GL20.glGetShaderSource(shader, maxLength);
	}
	
	public static void glGetShaderSource(int shader, IntBuffer length, ByteBuffer source)
	{
		GL20.glGetShaderSource(shader, length, source);
		
	}
	
	public static String glGetString(int name)
	{
		return GL11.glGetString(name);
	}
	
	public static String glGetStringi(int name, int index)
	{
		return GL30.glGetStringi(name, index);
	}
	
	public static int glGetSubroutineIndex(int program, int shadertype, ByteBuffer name)
	{
		return GL40.glGetSubroutineIndex(program, shadertype, name);
	}
	
	public static int glGetSubroutineIndex(int program, int shadertype, CharSequence name)
	{
		return GL40.glGetSubroutineIndex(program, shadertype, name);
	}
	
	public static int glGetSubroutineUniformLocation(int program, int shadertype, ByteBuffer name)
	{
		return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
	}
	
	public static int glGetSubroutineUniformLocation(int program, int shadertype, CharSequence name)
	{
		return GL40.glGetSubroutineUniformLocation(program, shadertype, name);
	}
	
	public static void glGetSync(GLSync sync, int pname, IntBuffer length, IntBuffer values)
	{
		GL32.glGetSync(sync, pname, length, values);
		
	}
	
	public static int glGetSynci(GLSync sync, int pname)
	{
		return GL32.glGetSynci(sync, pname);
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, DoubleBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, FloatBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, IntBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, long pixels_buffer_offset)
	{
		GL11.glGetTexImage(target, level, format, type, pixels_buffer_offset);
		
	}
	
	public static void glGetTexImage(int target, int level, int format, int type, ShortBuffer pixels)
	{
		GL11.glGetTexImage(target, level, format, type, pixels);
		
	}
	
	public static void glGetTexLevelParameter(int target, int level, int pname, FloatBuffer params)
	{
		GL11.glGetTexLevelParameter(target, level, pname, params);
		
	}
	
	public static void glGetTexLevelParameter(int target, int level, int pname, IntBuffer params)
	{
		GL11.glGetTexLevelParameter(target, level, pname, params);
		
	}
	
	public static float glGetTexLevelParameterf(int target, int level, int pname)
	{
		return GL11.glGetTexLevelParameterf(target, level, pname);
	}
	
	public static int glGetTexLevelParameteri(int target, int level, int pname)
	{
		return GL11.glGetTexLevelParameteri(target, level, pname);
	}
	
	public static void glGetTexParameter(int target, int pname, FloatBuffer params)
	{
		GL11.glGetTexParameter(target, pname, params);
		
	}
	
	public static void glGetTexParameter(int target, int pname, IntBuffer params)
	{
		GL11.glGetTexParameter(target, pname, params);
		
	}
	
	public static float glGetTexParameterf(int target, int pname)
	{
		return GL11.glGetTexParameterf(target, pname);
	}
	
	public static int glGetTexParameteri(int target, int pname)
	{
		return GL11.glGetTexParameteri(target, pname);
	}
	
	public static void glGetTexParameterI(int target, int pname, IntBuffer params)
	{
		GL30.glGetTexParameterI(target, pname, params);
		
	}
	
	public static int glGetTexParameterIi(int target, int pname)
	{
		return GL30.glGetTexParameterIi(target, pname);
	}
	
	public static void glGetTexParameterIu(int target, int pname, IntBuffer params)
	{
		GL30.glGetTexParameterIu(target, pname, params);
		
	}
	
	public static int glGetTexParameterIui(int target, int pname)
	{
		return GL30.glGetTexParameterIui(target, pname);
	}
	
	public static String glGetTransformFeedbackVarying(int program, int index, int bufSize, IntBuffer size, IntBuffer type)
	{
		return GL30.glGetTransformFeedbackVarying(program, index, bufSize, size, type);
	}
	
	public static void glGetTransformFeedbackVarying(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL30.glGetTransformFeedbackVarying(program, index, length, size, type, name);
		
	}
	
	public static void glGetUniform(int program, int location, DoubleBuffer params)
	{
		GL40.glGetUniform(program, location, params);
		
	}
	
	public static void glGetUniform(int program, int location, FloatBuffer params)
	{
		GL20.glGetUniform(program, location, params);
		
	}
	
	public static void glGetUniform(int program, int location, IntBuffer params)
	{
		GL20.glGetUniform(program, location, params);
		
	}
	
	public static int glGetUniformBlockIndex(int program, ByteBuffer uniformBlockName)
	{
		return GL31.glGetUniformBlockIndex(program, uniformBlockName);
	}
	
	public static int glGetUniformBlockIndex(int program, CharSequence uniformBlockName)
	{
		return GL31.glGetUniformBlockIndex(program, uniformBlockName);
	}
	
	public static void glGetUniformIndices(int program, ByteBuffer uniformNames, IntBuffer uniformIndices)
	{
		GL31.glGetUniformIndices(program, uniformNames, uniformIndices);
		
	}
	
	public static void glGetUniformIndices(int program, CharSequence[] uniformNames, IntBuffer uniformIndices)
	{
		GL31.glGetUniformIndices(program, uniformNames, uniformIndices);
		
	}
	
	public static int glGetUniformLocation(int program, ByteBuffer name)
	{
		return GL20.glGetUniformLocation(program, name);
	}
	
	public static int glGetUniformLocation(int program, CharSequence name)
	{
		return GL20.glGetUniformLocation(program, name);
	}
	
	public static void glGetUniformSubroutineu(int shadertype, int location, IntBuffer params)
	{
		GL40.glGetUniformSubroutineu(shadertype, location, params);
		
	}
	
	public static int glGetUniformSubroutineui(int shadertype, int location)
	{
		return GL40.glGetUniformSubroutineui(shadertype, location);
	}
	
	public static void glGetUniformu(int program, int location, IntBuffer params)
	{
		GL30.glGetUniformu(program, location, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, DoubleBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, FloatBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, IntBuffer params)
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
	}
	
	public static void glGetVertexAttribI(int index, int pname, IntBuffer params)
	{
		GL30.glGetVertexAttribI(index, pname, params);
		
	}
	
	public static void glGetVertexAttribIu(int index, int pname, IntBuffer params)
	{
		GL30.glGetVertexAttribIu(index, pname, params);
		
	}
	
	public static void glGetVertexAttribL(int index, int pname, DoubleBuffer params)
	{
		GL41.glGetVertexAttribL(index, pname, params);
		
	}
	
	public static void glGetVertexAttribPointer(int index, int pname, ByteBuffer pointer)
	{
		GL20.glGetVertexAttribPointer(index, pname, pointer);
		
	}
	
	public static ByteBuffer glGetVertexAttribPointer(int index, int pname, long result_size)
	{
		return GL20.glGetVertexAttribPointer(index, pname, result_size);
	}
	
	//XXX H
	
	public static void glHint(int target, int mode)
	{
		GL11.glHint(target, mode);
		
	}
	
	//XXX I
	
	public static void glInvalidateBufferData(int buffer)
	{
		GL43.glInvalidateBufferData(buffer);
		
	}
	
	public static void glInvalidateBufferSubData(int buffer, long offset, long length)
	{
		GL43.glInvalidateBufferSubData(buffer, offset, length);
		
	}
	
	public static void glInvalidateFramebuffer(int target, IntBuffer attachments)
	{
		GL43.glInvalidateFramebuffer(target, attachments);
		
	}
	
	public static void glInvalidateSubFramebuffer(int target, IntBuffer attachments, int x, int y, int width, int height)
	{
		GL43.glInvalidateSubFramebuffer(target, attachments, x, y, width, height);
		
	}
	
	public static void glInvalidateTexImage(int texture, int level)
	{
		GL43.glInvalidateTexImage(texture, level);
		
	}
	
	public static void glInvalidateTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth)
	{
		GL43.glInvalidateTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth);
		
	}
	
	public static boolean glIsBuffer(int buffer)
	{
		return GL15.glIsBuffer(buffer);
	}
	
	public static boolean glIsEnabled(int cap)
	{
		return GL11.glIsEnabled(cap);
	}
	
	public static boolean glIsEnabledi(int target, int index)
	{
		return GL30.glIsEnabledi(target, index);
	}
	
	public static boolean glIsFramebuffer(int framebuffer)
	{
		return GL30.glIsFramebuffer(framebuffer);
	}
	
	public static boolean glIsProgram(int program)
	{
		return GL20.glIsProgram(program);
	}
	
	public static boolean glIsProgramPipeline(int pipeline)
	{
		return GL41.glIsProgramPipeline(pipeline);
	}
	
	public static boolean glIsQuery(int id)
	{
		return GL15.glIsQuery(id);
	}
	
	public static boolean glIsRenderbuffer(int renderbuffer)
	{
		return GL30.glIsRenderbuffer(renderbuffer);
	}
	
	public static boolean glIsSampler(int sampler)
	{
		return GL33.glIsSampler(sampler);
	}
	
	public static boolean glIsShader(int shader)
	{
		return GL20.glIsShader(shader);
	}
	
	public static boolean glIsSync(GLSync sync)
	{
		return GL32.glIsSync(sync);
	}
	
	public static boolean glIsTexture(int texture)
	{
		return GL11.glIsTexture(texture);
	}
	
	public static boolean glIsTransformFeedback(int id)
	{
		return GL40.glIsTransformFeedback(id);
	}
	
	public static boolean glIsVertexArray(int array)
	{
		return GL30.glIsVertexArray(array);
	}
	
	//XXX L
	
	public static void glLineWidth(float width)
	{
		GL11.glLineWidth(width);
		
	}
	
	public static void glLinkProgram(int program)
	{
		GL20.glLinkProgram(program);
		
	}
	
	public static void glLinkProgram(GLProgram program)
	{
		glLinkProgram(program.getId());
		
	}
	
	public static void glLogicOp(int opcode)
	{
		GL11.glLogicOp(opcode);
		
	}
	
	//XXX M
	
	public static ByteBuffer glMapBuffer(int target, int access, ByteBuffer old_buffer)
	{
		return GL15.glMapBuffer(target, access, old_buffer);
	}
	
	public static ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer old_buffer)
	{
		return GL15.glMapBuffer(target, access, length, old_buffer);
	}
	
	public static ByteBuffer glMapBufferRange(int target, long offset, long length, int access, ByteBuffer old_buffer)
	{
		return GL30.glMapBufferRange(target, offset, length, access, old_buffer);
	}
	
	public static void glMemoryBarrier(int barriers)
	{
		GL42.glMemoryBarrier(barriers);
		
	}
	
	public static void glMinSampleShading(float value)
	{
		GL40.glMinSampleShading(value);
		
	}
	
	public static void glMultiDrawArrays(int mode, IntBuffer piFirst, IntBuffer piCount)
	{
		GL14.glMultiDrawArrays(mode, piFirst, piCount);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, ByteBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, IntBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawArraysIndirect(int mode, long indirect_buffer_offset, int primcount, int stride)
	{
		GL43.glMultiDrawArraysIndirect(mode, indirect_buffer_offset, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, ByteBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, IntBuffer indirect, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect, primcount, stride);
		
	}
	
	public static void glMultiDrawElementsIndirect(int mode, int type, long indirect_buffer_offset, int primcount, int stride)
	{
		GL43.glMultiDrawElementsIndirect(mode, type, indirect_buffer_offset, primcount, stride);
		
	}
	
	//XXX N
	
	//XXX O
	
	public static void glObjectLabel(int identifier, int name, ByteBuffer label)
	{
		GL43.glObjectLabel(identifier, name, label);
		
	}
	
	public static void glObjectLabel(int identifier, int name, CharSequence label)
	{
		GL43.glObjectLabel(identifier, name, label);
		
	}
	
	public static void glObjectPtrLabel(PointerWrapper ptr, ByteBuffer label)
	{
		GL43.glObjectPtrLabel(ptr, label);
		
	}
	
	public static void glObjectPtrLabel(PointerWrapper ptr, CharSequence label)
	{
		GL43.glObjectPtrLabel(ptr, label);
		
	}
	
	//XXX P
	
	public static void glPatchParameter(int pname, FloatBuffer values)
	{
		GL40.glPatchParameter(pname, values);
		
	}
	
	public static void glPatchParameteri(int pname, int value)
	{
		GL40.glPatchParameteri(pname, value);
		
	}
	
	public static void glPauseTransformFeedback()
	{
		GL40.glPauseTransformFeedback();
		
	}
	
	public static void glPixelStoref(int pname, float param)
	{
		GL11.glPixelStoref(pname, param);
		
	}
	
	public static void glPixelStorei(int pname, int param)
	{
		GL11.glPixelStorei(pname, param);
		
	}
	
	public static void glPointParameter(int pname, FloatBuffer params)
	{
		GL14.glPointParameter(pname, params);
		
	}
	
	public static void glPointParameter(int pname, IntBuffer params)
	{
		GL14.glPointParameter(pname, params);
		
	}
	
	public static void glPointParameterf(int pname, float param)
	{
		GL14.glPointParameterf(pname, param);
		
	}
	
	public static void glPointParameteri(int pname, int param)
	{
		GL14.glPointParameteri(pname, param);
		
	}
	
	public static void glPointSize(float size)
	{
		GL11.glPointSize(size);
		
	}
	
	public static void glPolygonMode(int face, int mode)
	{
		GL11.glPolygonMode(face, mode);
		
	}
	
	public static void glPolygonOffset(float factor, float units)
	{
		GL11.glPolygonOffset(factor, units);
		
	}
	
	public static void glPopDebugGroup()
	{
		GL43.glPopDebugGroup();
		
	}
	
	public static void glPrimitiveRestartIndex(int index)
	{
		GL31.glPrimitiveRestartIndex(index);
		
	}
	
	public static void glProgramBinary(int program, int binaryFormat, ByteBuffer binary)
	{
		GL41.glProgramBinary(program, binaryFormat, binary);
		
	}
	
	public static void glProgramParameteri(int program, int pname, int value)
	{
		GL41.glProgramParameteri(program, pname, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform1(program, location, value);
		
	}
	
	public static void glProgramUniform1d(int program, int location, double v0)
	{
		GL41.glProgramUniform1d(program, location, v0);
		
	}
	
	public static void glProgramUniform1f(int program, int location, float v0)
	{
		GL41.glProgramUniform1f(program, location, v0);
		
	}
	
	public static void glProgramUniform1i(int program, int location, int v0)
	{
		GL41.glProgramUniform1i(program, location, v0);
		
	}
	
	public static void glProgramUniform1u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform1u(program, location, value);
		
	}
	
	public static void glProgramUniform1ui(int program, int location, int v0)
	{
		GL41.glProgramUniform1ui(program, location, v0);
		
	}
	
	public static void glProgramUniform2(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform2(program, location, value);
		
	}
	
	public static void glProgramUniform2d(int program, int location, double v0, double v1)
	{
		GL41.glProgramUniform2d(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2f(int program, int location, float v0, float v1)
	{
		GL41.glProgramUniform2f(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2i(int program, int location, int v0, int v1)
	{
		GL41.glProgramUniform2i(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform2u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform2u(program, location, value);
		
	}
	
	public static void glProgramUniform2ui(int program, int location, int v0, int v1)
	{
		GL41.glProgramUniform2ui(program, location, v0, v1);
		
	}
	
	public static void glProgramUniform3(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform3(program, location, value);
		
	}
	
	public static void glProgramUniform3d(int program, int location, double v0, double v1, double v2)
	{
		GL41.glProgramUniform3d(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3f(int program, int location, float v0, float v1, float v2)
	{
		GL41.glProgramUniform3f(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3i(int program, int location, int v0, int v1, int v2)
	{
		GL41.glProgramUniform3i(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform3u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform3u(program, location, value);
		
	}
	
	public static void glProgramUniform3ui(int program, int location, int v0, int v1, int v2)
	{
		GL41.glProgramUniform3ui(program, location, v0, v1, v2);
		
	}
	
	public static void glProgramUniform4(int program, int location, DoubleBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4(int program, int location, FloatBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform4(program, location, value);
		
	}
	
	public static void glProgramUniform4d(int program, int location, double v0, double v1, double v2, double v3)
	{
		GL41.glProgramUniform4d(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4f(int program, int location, float v0, float v1, float v2, float v3)
	{
		GL41.glProgramUniform4f(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4i(int program, int location, int v0, int v1, int v2, int v3)
	{
		GL41.glProgramUniform4i(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniform4u(int program, int location, IntBuffer value)
	{
		GL41.glProgramUniform4u(program, location, value);
		
	}
	
	public static void glProgramUniform4ui(int program, int location, int v0, int v1, int v2, int v3)
	{
		GL41.glProgramUniform4ui(program, location, v0, v1, v2, v3);
		
	}
	
	public static void glProgramUniformMatrix2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix2x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix2x4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix2x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix3x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix3x4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix3x4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x2(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x2(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4x2(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x3(int program, int location, boolean transpose, DoubleBuffer value)
	{
		GL41.glProgramUniformMatrix4x3(program, location, transpose, value);
		
	}
	
	public static void glProgramUniformMatrix4x3(int program, int location, boolean transpose, FloatBuffer value)
	{
		GL41.glProgramUniformMatrix4x3(program, location, transpose, value);
		
	}
	
	public static void glProvokingVertex(int mode)
	{
		GL32.glProvokingVertex(mode);
		
	}
	
	public static void glPushDebugGroup(int source, int id, ByteBuffer message)
	{
		GL43.glPushDebugGroup(source, id, message);
		
	}
	
	public static void glPushDebugGroup(int source, int id, CharSequence message)
	{
		GL43.glPushDebugGroup(source, id, message);
		
	}
	
	//XXX Q
	
	public static void glQueryCounter(int id, int target)
	{
		GL33.glQueryCounter(id, target);
		
	}
	
	//XXX R
	
	public static void glReadBuffer(int mode)
	{
		GL11.glReadBuffer(mode);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, DoubleBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, long pixels_buffer_offset)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels_buffer_offset);
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	public static void glReleaseShaderCompiler()
	{
		GL41.glReleaseShaderCompiler();
		
	}
	
	public static void glRenderbufferStorage(int target, int internalformat, int width, int height)
	{
		GL30.glRenderbufferStorage(target, internalformat, width, height);
		
	}
	
	public static void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height)
	{
		GL30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
		
	}
	
	public static void glResumeTransformFeedback()
	{
		GL40.glResumeTransformFeedback();
		
	}
	
	//XXX S
	
	public static void glSampleCoverage(float value, boolean invert)
	{
		GL13.glSampleCoverage(value, invert);
		
	}
	
	public static void glSampleMaski(int index, int mask)
	{
		GL32.glSampleMaski(index, mask);
		
	}
	
	public static void glSamplerParameter(int sampler, int pname, FloatBuffer params)
	{
		GL33.glSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glSamplerParameter(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameter(sampler, pname, params);
		
	}
	
	public static void glSamplerParameterf(int sampler, int pname, float param)
	{
		GL33.glSamplerParameterf(sampler, pname, param);
		
	}
	
	public static void glSamplerParameteri(int sampler, int pname, int param)
	{
		GL33.glSamplerParameteri(sampler, pname, param);
		
	}
	
	public static void glSamplerParameterI(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameterI(sampler, pname, params);
		
	}
	
	public static void glSamplerParameterIu(int sampler, int pname, IntBuffer params)
	{
		GL33.glSamplerParameterIu(sampler, pname, params);
		
	}
	
	public static void glScissor(int x, int y, int width, int height)
	{
		GL11.glScissor(x, y, width, height);
		
	}
	
	public static void glScissorArray(int first, IntBuffer v)
	{
		GL41.glScissorArray(first, v);
		
	}
	
	public static void glScissorIndexed(int index, int left, int bottom, int width, int height)
	{
		GL41.glScissorIndexed(index, left, bottom, width, height);
		
	}
	
	public static void glScissorIndexed(int index, IntBuffer v)
	{
		GL41.glScissorIndexed(index, v);
		
	}
	
	public static void glShaderBinary(IntBuffer shaders, int binaryformat, ByteBuffer binary)
	{
		GL41.glShaderBinary(shaders, binaryformat, binary);
		
	}
	
	public static void glShaderSource(int shader, ByteBuffer string)
	{
		GL20.glShaderSource(shader, string);
		
	}
	
	public static void glShaderSource(int shader, CharSequence string)
	{
		GL20.glShaderSource(shader, string);
		
	}
	
	public static void glShaderSource(int shader, CharSequence[] strings)
	{
		GL20.glShaderSource(shader, strings);
		
	}
	
	public static void glShaderStorageBlockBinding(int program, int storageBlockIndex, int storageBlockBinding)
	{
		GL43.glShaderStorageBlockBinding(program, storageBlockIndex, storageBlockBinding);
		
	}
	
	public static void glStencilFunc(int func, int ref, int mask)
	{
		GL11.glStencilFunc(func, ref, mask);
		
	}
	
	public static void glStencilFuncSeparate(int face, int func, int ref, int mask)
	{
		GL20.glStencilFuncSeparate(face, func, ref, mask);
		
	}
	
	public static void glStencilMask(int mask)
	{
		GL11.glStencilMask(mask);
		
	}
	
	public static void glStencilMaskSeparate(int face, int mask)
	{
		GL20.glStencilMaskSeparate(face, mask);
		
	}
	
	public static void glStencilOp(int fail, int zfail, int zpass)
	{
		GL11.glStencilOp(fail, zfail, zpass);
		
	}
	
	public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass)
	{
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
	}
	
	//XXX T
	
	public static void glTexBuffer(int target, int internalformat, int buffer)
	{
		GL31.glTexBuffer(target, internalformat, buffer);
		
	}
	
	public static void glTexBufferRange(int target, int internalformat, int buffer, long offset, long size)
	{
		GL43.glTexBufferRange(target, internalformat, buffer, offset, size);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, IntBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexImage1D(target, level, internalformat, width, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations)
	{
		GL32.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, long pixels_buffer_offset)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations)
	{
		GL32.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
	}
	
	public static void glTexParameter(int target, int pname, FloatBuffer param)
	{
		GL11.glTexParameter(target, pname, param);
		
	}
	
	public static void glTexParameter(int target, int pname, IntBuffer param)
	{
		GL11.glTexParameter(target, pname, param);
		
	}
	
	public static void glTexParameterf(int target, int pname, float param)
	{
		GL11.glTexParameterf(target, pname, param);
		
	}
	
	public static void glTexParameteri(int target, int pname, int param)
	{
		GL11.glTexParameteri(target, pname, param);
		
	}
	
	public static void glTexParameterI(int target, int pname, IntBuffer params)
	{
		GL30.glTexParameterI(target, pname, params);
		
	}
	
	public static void glTexParameterIi(int target, int pname, int param)
	{
		GL30.glTexParameterIi(target, pname, param);
		
	}
	
	public static void glTexParameterIu(int target, int pname, IntBuffer params)
	{
		GL30.glTexParameterIu(target, pname, params);
		
	}
	
	public static void glTexParameterIui(int target, int pname, int param)
	{
		GL30.glTexParameterIui(target, pname, param);
		
	}
	
	public static void glTexStorage1D(int target, int levels, int internalformat, int width)
	{
		GL42.glTexStorage1D(target, levels, internalformat, width);
		
	}
	
	public static void glTexStorage2D(int target, int levels, int internalformat, int width, int height)
	{
		GL42.glTexStorage2D(target, levels, internalformat, width, height);
		
	}
	
	public static void glTexStorage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations)
	{
		GL43.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
		
	}
	
	public static void glTexStorage3D(int target, int levels, int internalformat, int width, int height, int depth)
	{
		GL42.glTexStorage3D(target, levels, internalformat, width, height, depth);
		
	}
	
	public static void glTexStorage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations)
	{
		GL43.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, IntBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexSubImage1D(target, level, xoffset, width, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels_buffer_offset)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels_buffer_offset)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels_buffer_offset);
		
	}
	
	public static void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer pixels)
	{
		GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		
	}
	
	public static void glTextureView(int texture, int target, int origtexture, int internalformat, int minlevel, int numlevels, int minlayer, int numlayers)
	{
		GL43.glTextureView(texture, target, origtexture, internalformat, minlevel, numlevels, minlayer, numlayers);
		
	}
	
	public static void glTransformFeedbackVaryings(int program, CharSequence[] varyings, int bufferMode)
	{
		GL30.glTransformFeedbackVaryings(program, varyings, bufferMode);
		
	}
	
	public static void glTransformFeedbackVaryings(int program, int count, ByteBuffer varyings, int bufferMode)
	{
		GL30.glTransformFeedbackVaryings(program, count, varyings, bufferMode);
		
	}
	
	//XXX U
	
	public static void glUniform1(int location, DoubleBuffer value)
	{
		GL40.glUniform1(location, value);
		
	}
	
	public static void glUniform1(int location, FloatBuffer values)
	{
		GL20.glUniform1(location, values);
		
	}
	
	public static void glUniform1(int location, IntBuffer values)
	{
		GL20.glUniform1(location, values);
		
	}
	
	public static void glUniform1d(int location, double x)
	{
		GL40.glUniform1d(location, x);
		
	}
	
	public static void glUniform1f(int location, float v0)
	{
		GL20.glUniform1f(location, v0);
		
	}
	
	public static void glUniform1i(int location, int v0)
	{
		GL20.glUniform1i(location, v0);
		
	}
	
	public static void glUniform1u(int location, IntBuffer value)
	{
		GL30.glUniform1u(location, value);
		
	}
	
	public static void glUniform1ui(int location, int v0)
	{
		GL30.glUniform1ui(location, v0);
		
	}
	
	public static void glUniform2(int location, DoubleBuffer value)
	{
		GL40.glUniform2(location, value);
		
	}
	
	public static void glUniform2(int location, FloatBuffer values)
	{
		GL20.glUniform2(location, values);
		
	}
	
	public static void glUniform2(int location, IntBuffer values)
	{
		GL20.glUniform2(location, values);
		
	}
	
	public static void glUniform2d(int location, double x, double y)
	{
		GL40.glUniform2d(location, x, y);
		
	}
	
	public static void glUniform2f(int location, float v0, float v1)
	{
		GL20.glUniform2f(location, v0, v1);
		
	}
	
	public static void glUniform2i(int location, int v0, int v1)
	{
		GL20.glUniform2i(location, v0, v1);
		
	}
	
	public static void glUniform2u(int location, IntBuffer value)
	{
		GL30.glUniform2u(location, value);
		
	}
	
	public static void glUniform2ui(int location, int v0, int v1)
	{
		GL30.glUniform2ui(location, v0, v1);
		
	}
	
	public static void glUniform3(int location, DoubleBuffer value)
	{
		GL40.glUniform3(location, value);
		
	}
	
	public static void glUniform3(int location, FloatBuffer values)
	{
		GL20.glUniform3(location, values);
		
	}
	
	public static void glUniform3(int location, IntBuffer values)
	{
		GL20.glUniform3(location, values);
		
	}
	
	public static void glUniform3d(int location, double x, double y, double z)
	{
		GL40.glUniform3d(location, x, y, z);
		
	}
	
	public static void glUniform3f(int location, float v0, float v1, float v2)
	{
		GL20.glUniform3f(location, v0, v1, v2);
		
	}
	
	public static void glUniform3i(int location, int v0, int v1, int v2)
	{
		GL20.glUniform3i(location, v0, v1, v2);
		
	}
	
	public static void glUniform3u(int location, IntBuffer value)
	{
		GL30.glUniform3u(location, value);
		
	}
	
	public static void glUniform3ui(int location, int v0, int v1, int v2)
	{
		GL30.glUniform3ui(location, v0, v1, v2);
		
	}
	
	public static void glUniform4(int location, DoubleBuffer value)
	{
		GL40.glUniform4(location, value);
		
	}
	
	public static void glUniform4(int location, FloatBuffer values)
	{
		GL20.glUniform4(location, values);
		
	}
	
	public static void glUniform4(int location, IntBuffer values)
	{
		GL20.glUniform4(location, values);
		
	}
	
	public static void glUniform4d(int location, double x, double y, double z, double w)
	{
		GL40.glUniform4d(location, x, y, z, w);
		
	}
	
	public static void glUniform4f(int location, float v0, float v1, float v2, float v3)
	{
		GL20.glUniform4f(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniform4i(int location, int v0, int v1, int v2, int v3)
	{
		GL20.glUniform4i(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniform4u(int location, IntBuffer value)
	{
		GL30.glUniform4u(location, value);
		
	}
	
	public static void glUniform4ui(int location, int v0, int v1, int v2, int v3)
	{
		GL30.glUniform4ui(location, v0, v1, v2, v3);
		
	}
	
	public static void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding)
	{
		GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		
	}
	
	public static void glUniformMatrix2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix2x3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2x3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2x3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix2x3(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix2x4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix2x4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix2x4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix2x4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix3(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3x2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3x2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3x2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix3x2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix3x4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix3x4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix3x4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix3x4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices)
	{
		GL20.glUniformMatrix4(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4x2(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4x2(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4x2(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix4x2(location, transpose, matrices);
		
	}
	
	public static void glUniformMatrix4x3(int location, boolean transpose, DoubleBuffer value)
	{
		GL40.glUniformMatrix4x3(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4x3(int location, boolean transpose, FloatBuffer matrices)
	{
		GL21.glUniformMatrix4x3(location, transpose, matrices);
		
	}
	
	public static void glUniformSubroutinesu(int shadertype, IntBuffer indices)
	{
		GL40.glUniformSubroutinesu(shadertype, indices);
		
	}
	
	public static boolean glUnmapBuffer(int target)
	{
		return GL15.glUnmapBuffer(target);
	}
	
	public static void glUseProgram(int program)
	{
		GL20.glUseProgram(program);
		
	}
	
	public static void glUseProgram(GLProgram program)
	{
		glUseProgram(program.getId());
		
	}
	
	public static void glUseProgramStages(int pipeline, int stages, int program)
	{
		GL41.glUseProgramStages(pipeline, stages, program);
		
	}
	
	//XXX V
	
	public static void glValidateProgram(int program)
	{
		GL20.glValidateProgram(program);
		
	}
	
	public static void glValidateProgram(GLProgram program)
	{
		glValidateProgram(program.getId());
		
	}
	
	public static void glValidateProgramPipeline(int pipeline)
	{
		GL41.glValidateProgramPipeline(pipeline);
		
	}
	
	public static void glVertexAttrib1d(int index, double x)
	{
		GL20.glVertexAttrib1d(index, x);
		
	}
	
	public static void glVertexAttrib1f(int index, float x)
	{
		GL20.glVertexAttrib1f(index, x);
		
	}
	
	public static void glVertexAttrib1s(int index, short x)
	{
		GL20.glVertexAttrib1s(index, x);
		
	}
	
	public static void glVertexAttrib2d(int index, double x, double y)
	{
		GL20.glVertexAttrib2d(index, x, y);
		
	}
	
	public static void glVertexAttrib2f(int index, float x, float y)
	{
		GL20.glVertexAttrib2f(index, x, y);
		
	}
	
	public static void glVertexAttrib2s(int index, short x, short y)
	{
		GL20.glVertexAttrib2s(index, x, y);
		
	}
	
	public static void glVertexAttrib3d(int index, double x, double y, double z)
	{
		GL20.glVertexAttrib3d(index, x, y, z);
		
	}
	
	public static void glVertexAttrib3f(int index, float x, float y, float z)
	{
		GL20.glVertexAttrib3f(index, x, y, z);
		
	}
	
	public static void glVertexAttrib3s(int index, short x, short y, short z)
	{
		GL20.glVertexAttrib3s(index, x, y, z);
		
	}
	
	public static void glVertexAttrib4d(int index, double x, double y, double z, double w)
	{
		GL20.glVertexAttrib4d(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4f(int index, float x, float y, float z, float w)
	{
		GL20.glVertexAttrib4f(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4Nub(int index, byte x, byte y, byte z, byte w)
	{
		GL20.glVertexAttrib4Nub(index, x, y, z, w);
		
	}
	
	public static void glVertexAttrib4s(int index, short x, short y, short z, short w)
	{
		GL20.glVertexAttrib4s(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribBinding(int attribindex, int bindingindex)
	{
		GL43.glVertexAttribBinding(attribindex, bindingindex);
		
	}
	
	public static void glVertexAttribDivisor(int index, int divisor)
	{
		GL33.glVertexAttribDivisor(index, divisor);
		
	}
	
	public static void glVertexAttribFormat(int attribindex, int size, int type, boolean normalized, int relativeoffset)
	{
		GL43.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
		
	}
	
	public static void glVertexAttribI1(int index, IntBuffer v)
	{
		GL30.glVertexAttribI1(index, v);
		
	}
	
	public static void glVertexAttribI1i(int index, int x)
	{
		GL30.glVertexAttribI1i(index, x);
		
	}
	
	public static void glVertexAttribI1u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI1u(index, v);
		
	}
	
	public static void glVertexAttribI1ui(int index, int x)
	{
		GL30.glVertexAttribI1ui(index, x);
		
	}
	
	public static void glVertexAttribI2(int index, IntBuffer v)
	{
		GL30.glVertexAttribI2(index, v);
		
	}
	
	public static void glVertexAttribI2i(int index, int x, int y)
	{
		GL30.glVertexAttribI2i(index, x, y);
		
	}
	
	public static void glVertexAttribI2u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI2u(index, v);
		
	}
	
	public static void glVertexAttribI2ui(int index, int x, int y)
	{
		GL30.glVertexAttribI2ui(index, x, y);
		
	}
	
	public static void glVertexAttribI3(int index, IntBuffer v)
	{
		GL30.glVertexAttribI3(index, v);
		
	}
	
	public static void glVertexAttribI3i(int index, int x, int y, int z)
	{
		GL30.glVertexAttribI3i(index, x, y, z);
		
	}
	
	public static void glVertexAttribI3u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI3u(index, v);
		
	}
	
	public static void glVertexAttribI3ui(int index, int x, int y, int z)
	{
		GL30.glVertexAttribI3ui(index, x, y, z);
		
	}
	
	public static void glVertexAttribI4(int index, ByteBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4(int index, IntBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4(int index, ShortBuffer v)
	{
		GL30.glVertexAttribI4(index, v);
		
	}
	
	public static void glVertexAttribI4i(int index, int x, int y, int z, int w)
	{
		GL30.glVertexAttribI4i(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribI4u(int index, ByteBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4u(int index, IntBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4u(int index, ShortBuffer v)
	{
		GL30.glVertexAttribI4u(index, v);
		
	}
	
	public static void glVertexAttribI4ui(int index, int x, int y, int z, int w)
	{
		GL30.glVertexAttribI4ui(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribIFormat(int attribindex, int size, int type, int relativeoffset)
	{
		GL43.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, ByteBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, long buffer_buffer_offset)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer_buffer_offset);
		
	}
	
	public static void glVertexAttribIPointer(int index, int size, int type, int stride, ShortBuffer buffer)
	{
		GL30.glVertexAttribIPointer(index, size, type, stride, buffer);
		
	}
	
	public static void glVertexAttribL1(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL1(index, v);
		
	}
	
	public static void glVertexAttribL1d(int index, double x)
	{
		GL41.glVertexAttribL1d(index, x);
		
	}
	
	public static void glVertexAttribL2(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL2(index, v);
		
	}
	
	public static void glVertexAttribL2d(int index, double x, double y)
	{
		GL41.glVertexAttribL2d(index, x, y);
		
	}
	
	public static void glVertexAttribL3(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL3(index, v);
		
	}
	
	public static void glVertexAttribL3d(int index, double x, double y, double z)
	{
		GL41.glVertexAttribL3d(index, x, y, z);
		
	}
	
	public static void glVertexAttribL4(int index, DoubleBuffer v)
	{
		GL41.glVertexAttribL4(index, v);
		
	}
	
	public static void glVertexAttribL4d(int index, double x, double y, double z, double w)
	{
		GL41.glVertexAttribL4d(index, x, y, z, w);
		
	}
	
	public static void glVertexAttribLFormat(int attribindex, int size, int type, int relativeoffset)
	{
		GL43.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
		
	}
	
	public static void glVertexAttribLPointer(int index, int size, int stride, DoubleBuffer pointer)
	{
		GL41.glVertexAttribLPointer(index, size, stride, pointer);
		
	}
	
	public static void glVertexAttribLPointer(int index, int size, int stride, long pointer_buffer_offset)
	{
		GL41.glVertexAttribLPointer(index, size, stride, pointer_buffer_offset);
		
	}
	
	public static void glVertexAttribP1u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP1u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP1ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP1ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP2u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP2u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP2ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP2ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP3u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP3u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP3ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP3ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP4u(int index, int type, boolean normalized, IntBuffer value)
	{
		GL33.glVertexAttribP4u(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribP4ui(int index, int type, boolean normalized, int value)
	{
		GL33.glVertexAttribP4ui(index, type, normalized, value);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, ByteBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, IntBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean unsigned, boolean normalized, int stride, ShortBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, unsigned, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean normalized, int stride, DoubleBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, boolean normalized, int stride, FloatBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer buffer)
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, buffer);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long buffer_buffer_offset)
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, buffer_buffer_offset);
		
	}
	
	public static void glVertexBindingDivisor(int bindingindex, int divisor)
	{
		GL43.glVertexBindingDivisor(bindingindex, divisor);
		
	}
	
	public static void glVertexP2u(int type, IntBuffer value)
	{
		GL33.glVertexP2u(type, value);
		
	}
	
	public static void glVertexP2ui(int type, int value)
	{
		GL33.glVertexP2ui(type, value);
		
	}
	
	public static void glVertexP3u(int type, IntBuffer value)
	{
		GL33.glVertexP3u(type, value);
		
	}
	
	public static void glVertexP3ui(int type, int value)
	{
		GL33.glVertexP3ui(type, value);
		
	}
	
	public static void glVertexP4u(int type, IntBuffer value)
	{
		GL33.glVertexP4u(type, value);
		
	}
	
	public static void glVertexP4ui(int type, int value)
	{
		GL33.glVertexP4ui(type, value);
		
	}
	
	public static void glVertexPointer(int size, int stride, DoubleBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int stride, FloatBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int type, int stride, ByteBuffer pointer)
	{
		GL11.glVertexPointer(size, type, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int type, int stride, long pointer_buffer_offset)
	{
		GL11.glVertexPointer(size, type, stride, pointer_buffer_offset);
		
	}
	
	public static void glVertexPointer(int size, int stride, IntBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glVertexPointer(int size, int stride, ShortBuffer pointer)
	{
		GL11.glVertexPointer(size, stride, pointer);
		
	}
	
	public static void glViewport(int x, int y, int width, int height)
	{
		GL11.glViewport(x, y, width, height);
		
	}
	
	public static void glViewportArray(int first, FloatBuffer v)
	{
		GL41.glViewportArray(first, v);
		
	}
	
	public static void glViewportIndexed(int index, FloatBuffer v)
	{
		GL41.glViewportIndexed(index, v);
		
	}
	
	public static void glViewportIndexedf(int index, float x, float y, float w, float h)
	{
		GL41.glViewportIndexedf(index, x, y, w, h);
		
	}
	
	//XXX W
	
	public static void glWaitSync(GLSync sync, int flags, long timeout)
	{
		GL32.glWaitSync(sync, flags, timeout);
		
	}
	
}
