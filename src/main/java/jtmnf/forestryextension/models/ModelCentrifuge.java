package jtmnf.forestryextension.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCentrifuge extends ModelBase
{
    //fields
    ModelRenderer Front;
    ModelRenderer Back;
    ModelRenderer Middleone;
    ModelRenderer Middletwo;
    ModelRenderer Middlethree;
    ModelRenderer Middlefour;
    ModelRenderer Smallthree;
    ModelRenderer Smallone;
    ModelRenderer Smalltwo;
    ModelRenderer Smallfour;
    ModelRenderer Smallfive;

    public ModelCentrifuge()
    {
        textureWidth = 64;
        textureHeight = 64;

        Front = new ModelRenderer(this, 24, 44);
        Front.addBox(0F, 0F, 0F, 16, 16, 4);
        Front.setRotationPoint(-8F, 8F, -8F);
        Front.setTextureSize(64, 64);
        Front.mirror = true;
        setRotation(Front, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 26, 25);
        Back.addBox(0F, 0F, 0F, 16, 16, 3);
        Back.setRotationPoint(-8F, 8F, 5F);
        Back.setTextureSize(64, 64);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Middleone = new ModelRenderer(this, 30, 8);
        Middleone.addBox(0F, 0F, 0F, 16, 16, 1);
        Middleone.setRotationPoint(-8F, 8F, 3F);
        Middleone.setTextureSize(64, 64);
        Middleone.mirror = true;
        setRotation(Middleone, 0F, 0F, 0F);
        Middletwo = new ModelRenderer(this, 30, 8);
        Middletwo.addBox(0F, 0F, 0F, 16, 16, 1);
        Middletwo.setRotationPoint(-8F, 8F, 1F);
        Middletwo.setTextureSize(64, 64);
        Middletwo.mirror = true;
        setRotation(Middletwo, 0F, 0F, 0F);
        Middlethree = new ModelRenderer(this, 30, 8);
        Middlethree.addBox(0F, 0F, 0F, 16, 16, 1);
        Middlethree.setRotationPoint(-8F, 8F, -1F);
        Middlethree.setTextureSize(64, 64);
        Middlethree.mirror = true;
        setRotation(Middlethree, 0F, 0F, 0F);
        Middlefour = new ModelRenderer(this, 30, 8);
        Middlefour.addBox(0F, 0F, 0F, 16, 16, 1);
        Middlefour.setRotationPoint(-8F, 8F, -3F);
        Middlefour.setTextureSize(64, 64);
        Middlefour.mirror = true;
        setRotation(Middlefour, 0F, 0F, 0F);
        Smallthree = new ModelRenderer(this, 0, 31);
        Smallthree.addBox(0F, 0F, 0F, 12, 12, 1);
        Smallthree.setRotationPoint(-6F, 10F, 0F);
        Smallthree.setTextureSize(64, 64);
        Smallthree.mirror = true;
        setRotation(Smallthree, 0F, 0F, 0F);
        Smallone = new ModelRenderer(this, 0, 31);
        Smallone.addBox(0F, 0F, 0F, 12, 12, 1);
        Smallone.setRotationPoint(-6F, 10F, 4F);
        Smallone.setTextureSize(64, 64);
        Smallone.mirror = true;
        setRotation(Smallone, 0F, 0F, 0F);
        Smalltwo = new ModelRenderer(this, 0, 31);
        Smalltwo.addBox(0F, 0F, 0F, 12, 12, 1);
        Smalltwo.setRotationPoint(-6F, 10F, 2F);
        Smalltwo.setTextureSize(64, 64);
        Smalltwo.mirror = true;
        setRotation(Smalltwo, 0F, 0F, 0F);
        Smallfour = new ModelRenderer(this, 0, 31);
        Smallfour.addBox(0F, 0F, 0F, 12, 12, 1);
        Smallfour.setRotationPoint(-6F, 10F, -2F);
        Smallfour.setTextureSize(64, 64);
        Smallfour.mirror = true;
        setRotation(Smallfour, 0F, 0F, 0F);
        Smallfive = new ModelRenderer(this, 0, 31);
        Smallfive.addBox(0F, 0F, 0F, 12, 12, 1);
        Smallfive.setRotationPoint(-6F, 10F, -4F);
        Smallfive.setTextureSize(64, 64);
        Smallfive.mirror = true;
        setRotation(Smallfive, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Front.render(f5);
        Back.render(f5);
        Middleone.render(f5);
        Middletwo.render(f5);
        Middlethree.render(f5);
        Middlefour.render(f5);
        Smallthree.render(f5);
        Smallone.render(f5);
        Smalltwo.render(f5);
        Smallfour.render(f5);
        Smallfive.render(f5);
    }

    public void renderModel(float f){
        Front.render(f);
        Back.render(f);
        Middleone.render(f);
        Middletwo.render(f);
        Middlethree.render(f);
        Middlefour.render(f);
        Smallthree.render(f);
        Smallone.render(f);
        Smalltwo.render(f);
        Smallfour.render(f);
        Smallfive.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
